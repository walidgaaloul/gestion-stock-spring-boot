package com.gaaloul.gestiondestock.utils;

public interface Constants {

     String APP_ROOT = "gestiondestock/v1";
     String COMMANDE_FOURNISSEUR_ENDPOINT = APP_ROOT +  "/commandefournisseurs";
     String CREATE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/create";
     String FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{idFournisseur}";
     String FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{codeCommandefFurnisseur}";
     String FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/all";
     String DELETE_COMMANDE_FOURNISSEUR_ENDPOINT = APP_ROOT +  "/delete/{idCommandeFournisseur}";


     String ENTREPRISE_ENDPOINT = APP_ROOT +  "/entreprises";

     String FOURNISSEUR_ENDPOINT = APP_ROOT +  "/fournisseurs";

     String MVTSTK_ENDPOINT = APP_ROOT +  "/mvtStks";

     String UTILISATEUR_ENDPOINT = APP_ROOT +  "/utilisateurs";

     String VENTE_ENDPOINT = APP_ROOT +  "/ventes";

     String AUTHENTICATION_ENDPOINT = APP_ROOT +  "/auth";

}
