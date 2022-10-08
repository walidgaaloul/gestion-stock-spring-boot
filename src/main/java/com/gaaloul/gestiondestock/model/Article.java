package com.gaaloul.gestiondestock.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="article")
public class Article extends AbstractEntity{

    @Column(name = "codearticle")
    private String codeArticle;

    @Column(name = "designation")
    private String Designation;

    @Column(name = "prixunitaireht")
    private BigDecimal prixUnitaireHt;

    @Column(name = "tauxtva")
    private BigDecimal tauxTva;

    @Column(name = "prixunitairettc")
    private BigDecimal prixUnitaireTtc;

    @Column(name= "photo")
    private String Photo;

    @ManyToOne
    @JoinColumn(name = "idcategory")
    private Category category;

    @Column(name = "identreprise")
    private Integer idEntreprise;

    @OneToMany(mappedBy = "article")
    private List<LigneVente> ligneVentes;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeClient> ligneCommandeClients ;

    @OneToMany(mappedBy = "article")
    private List<LigneCommandeFournisseur> ligneCommandeFournisseurs ;

    @OneToMany(mappedBy = "article")
    private List<MvtStk>mvtStks ;


}
