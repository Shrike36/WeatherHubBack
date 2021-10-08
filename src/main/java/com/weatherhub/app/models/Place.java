package com.weatherhub.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "places",
        uniqueConstraints =
        {
                @UniqueConstraint(columnNames = {"userid", "coordinate"}),
                //@UniqueConstraint(columnNames = "coordinate")
        })
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Place {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "placesIdSeq", sequenceName = "places_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "placesIdSeq")
    private Integer id;

    @NotNull
    @Column(name = "userid", nullable=false)
    private Integer userId;

    @NotEmpty
    @Column(name = "coordinate",  nullable=false)
    private String coordinate;

    public Place() {
    }

    public Place(Integer userId, String coordinate) {
        this.coordinate = coordinate;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(id, place.id) && Objects.equals(userId, place.userId) && Objects.equals(coordinate, place.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, coordinate);
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", userId=" + userId +
                ", coordinate='" + coordinate + '\'' +
                '}';
    }
}
