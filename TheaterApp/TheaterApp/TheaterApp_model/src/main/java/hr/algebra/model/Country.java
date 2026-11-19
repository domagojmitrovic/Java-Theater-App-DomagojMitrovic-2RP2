package hr.algebra.model;

import java.io.Serializable;

public record Country(Long id,String countryCode, String stateName) implements Serializable {
    public Country(String countryCode,String stateName){
        this(null,countryCode,stateName);
    }

    @Override
    public String toString() {
        return stateName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder().id(id).countryCode(countryCode).stateName(stateName);
    }

    public static class Builder {
        private Long id;
        private String countryCode;
        private String stateName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder countryCode(String countryCode) {
            this.countryCode = countryCode;
            return this;
        }

        public Builder stateName(String stateName) {
            this.stateName = stateName;
            return this;
        }

        public Country build() {
            return new Country(id, countryCode, stateName);
        }
    }
}
