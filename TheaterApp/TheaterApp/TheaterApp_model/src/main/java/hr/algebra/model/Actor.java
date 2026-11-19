package hr.algebra.model;


import java.util.Objects;

public class Actor extends Person<Actor>{
    private String actorId;

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public Actor(String firstName, String lastName, String oib, String actorId) {
        super(firstName, lastName, oib);
        this.actorId = Objects.requireNonNull(actorId,"Assign an ID to this Actor");
    }
    public Actor(String firstName, String lastName, String oib) {
        super(firstName, lastName, oib);
        this.actorId = "";
    }

    public Actor(Long id, String firstName, String lastName, String oib, String actorId) {
        super(id, firstName, lastName, oib);
        this.actorId = Objects.requireNonNull(actorId,"Assign an ID to this Actor");
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(this.getOib(), actor.getOib())&&Objects.equals(this.getActorId(),actor.getActorId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOib()+actorId);
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
                .actorId(actorId);
    }

    public static class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String oib;
        private String actorId = "";

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

        public Builder actorId(String actorId) {
            this.actorId = actorId;
            return this;
        }

        public Actor build() {
            return new Actor(id, firstName, lastName, oib, actorId);
        }
    }
}
