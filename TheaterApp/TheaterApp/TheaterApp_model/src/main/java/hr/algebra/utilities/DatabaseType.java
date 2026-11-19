package hr.algebra.utilities;

public enum DatabaseType {
    PGSQL;

    public static DatabaseType fromString(String value){
        if(value==null||value.isBlank()){
            throw new IllegalArgumentException("DB_Type is not setup. Check the DB_TYPE setting in the environment or .env file");
        }
        return switch (value.trim().toLowerCase()){
            case"pg","postgres","pgsql"->PGSQL;
            default -> throw new IllegalArgumentException("Wrong db type");
        };
    }

}
