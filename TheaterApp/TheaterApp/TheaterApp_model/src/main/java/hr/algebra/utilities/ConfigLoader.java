package hr.algebra.utilities;

import hr.algebra.exceptions.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Reads application settings from environment variables and, as a fallback, from a {@code .env} file.
 * The file is looked up in the working directory and then in each parent directory, so it is found
 * whether the app is started from the project root or from a module directory.
 * Real environment variables always win over values from the file.
 */
public class ConfigLoader {
    private static final String ENV_FILE = ".env";
    private static final String ENV_FILE_OVERRIDE = "THEATER_ENV_FILE";
    private static final Logger log = LoggerFactory.getLogger(ConfigLoader.class);

    private final Map<String, String> fileValues;

    public ConfigLoader() {
        this.fileValues = findEnvFile().map(this::readEnvFile).orElseGet(() -> {
            log.warn("No '{}' file found, using environment variables only.", ENV_FILE);
            return Map.of();
        });
    }

    public String get(String key) {
        String value = System.getenv(key);
        if (value == null) {
            value = fileValues.get(key);
        }
        if (value == null || value.isBlank()) {
            throw new ConfigurationException("Missing setting '" + key + "'. Set it in the environment or in the .env file.");
        }
        return value;
    }

    public int getInt(String key) {
        String value = get(key);
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new ConfigurationException("Setting '" + key + "' must be a number but was '" + value + "'.", e);
        }
    }

    private Optional<Path> findEnvFile() {
        String override = System.getenv(ENV_FILE_OVERRIDE);
        if (override != null && !override.isBlank()) {
            return Optional.of(Path.of(override));
        }

        for (Path dir = Path.of("").toAbsolutePath(); dir != null; dir = dir.getParent()) {
            Path candidate = dir.resolve(ENV_FILE);
            if (Files.isRegularFile(candidate)) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }

    private Map<String, String> readEnvFile(Path file) {
        Map<String, String> values = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(file);
            lines.forEach(line -> parseLine(line, values));
        } catch (IOException e) {
            log.error("Failed to read env file '{}'.", file, e);
            throw new ConfigurationException("Failed to read env file '" + file + "'.", e);
        }
        log.info("Env file '{}' loaded successfully ({} keys)", file, values.size());
        return values;
    }

    private void parseLine(String line, Map<String, String> values) {
        String trimmed = line.strip();
        int separator = trimmed.indexOf('=');
        if (trimmed.isEmpty() || trimmed.startsWith("#") || separator < 1) {
            return;
        }

        String key = trimmed.substring(0, separator).strip();
        if (key.startsWith("export ")) {
            key = key.substring("export ".length()).strip();
        }
        values.put(key, unquote(trimmed.substring(separator + 1).strip()));
    }

    private String unquote(String value) {
        boolean quoted = value.length() >= 2
                && (value.startsWith("\"") && value.endsWith("\"") || value.startsWith("'") && value.endsWith("'"));
        return quoted ? value.substring(1, value.length() - 1) : value;
    }
}
