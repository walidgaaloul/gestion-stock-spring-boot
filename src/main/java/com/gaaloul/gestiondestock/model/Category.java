package com.gaaloul.gestiondestock.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="category")
public class Category extends AbstractEntity{

@Column(name = "codeCategory")
private String codeCategory;

@Column(name = "designation")
private String designation;

    @Column(name = "identreprise")
    private Integer idEntreprise;

@OneToMany(mappedBy = "category")
private List<Article> articles;
}
