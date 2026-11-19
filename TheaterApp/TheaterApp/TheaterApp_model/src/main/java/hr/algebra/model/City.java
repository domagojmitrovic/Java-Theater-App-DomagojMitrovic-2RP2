package hr.algebra.model;

public record City(Long id,String name,String postalCode) {
    public City (String name, String postalCode){
        this (null,name,postalCode);
    }

    public City {
        if(name==null||name.trim().isEmpty()){
            throw new IllegalArgumentException("Name must not be empty");
            }
        if(postalCode==null||postalCode.trim().isEmpty()){
            throw new IllegalArgumentException("Postal code must not be empty");
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder().id(id).name(name).postalCode(postalCode);
    }

    public static class Builder {
        private Long id;
        private String name;
        private String postalCode;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public City build() {
            return new City(id, name, postalCode);
        }
    }
}
