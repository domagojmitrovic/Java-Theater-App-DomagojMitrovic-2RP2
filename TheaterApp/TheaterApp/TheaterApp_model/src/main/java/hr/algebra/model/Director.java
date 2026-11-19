package hr.algebra.model;

import java.util.Objects;

public class Director extends Person<Director>{
    private DirectionStyle directionStyle;
    private String directorId;

    public Director(String firstName, String lastName, String oib, DirectionStyle directionStyle,String directorId) {
        super(firstName, lastName, oib);
        this.directionStyle = directionStyle;
        this.directorId=directorId;
    }

    public Director(Long id, String firstName, String lastName, String oib, DirectionStyle directionStyle, String directorId) {
        super(id, firstName, lastName, oib);
        this.directionStyle = directionStyle;
        this.directorId=directorId;

    }

    public String getDirectorId() {
        return directorId;
    }

    public void setDirectorId(String directorId) {
        this.directorId = directorId;
    }

    public DirectionStyle getDirectionStyle() {
        return directionStyle;
    }

    public void setDirectionStyle(DirectionStyle directionStyle) {
        this.directionStyle = directionStyle;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Director director = (Director) o;
        return Objects.equals(this.getOib(), director.getOib())&&Objects.equals(this.getDirectorId(),director.getDirectorId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.getOib()+this.directorId);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return builder()
                .id(getId())
                .firstName(getFirstName())
                .lastName(getLastName())
                .oib(getOib())
                .directionStyle(directionStyle)
                .directorId(directorId);
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String oib;
        private DirectionStyle directionStyle;
        private String directorId;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder oib(String oib) {
            this.oib = oib;
            return this;
        }

        public Builder directionStyle(DirectionStyle directionStyle) {
            this.directionStyle = directionStyle;
            return this;
        }

        public Builder directorId(String directorId) {
            this.directorId = directorId;
            return this;
        }

        public Director build() {
            return new Director(id, firstName, lastName, oib, directionStyle, directorId);
        }
    }
}
