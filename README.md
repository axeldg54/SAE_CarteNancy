# SAE_CarteNancy
**Groupe** : AIL-2  
**Date** : 20/06/2024

## Equipe
- BAUDSON Dylan 
- DUNG Axel 
- FONTANEZ Antoine 
- FROGER Corentin
- PIERROT Nathan

## Présentation du projet  
Nous proposons un site web intéractif qui contient une carte de Nancy. Sur cette carte se trouvent des informations pratiques concernant, entre autres, les stations de velib de Nancy, certains restaurants et la météo.  
Il est même possible pour les utilisateurs de reserver la table d'un restaurant directement depuis notre application.  
Le site fonctionne avec du Javascript pour récuprérer les données utilisées, mais aussi avec des services RMI pour contourner les problèmes de cors que nous avons recontrés.  
  
## Fonctionnalitées  
Voici la liste des fonctionnalités que nous avons intégrées dans notre site web :
-  visionner des informations sur les stations de velib de Nancy :
    -  adresse
    -  nombre de vélos disponibles
    -  nombre de places de parking de libres
-  voir la position de certains restaurants de Nancy
-  permettre aux utilisateurs de rajouter un nouveau restaurant à la carte intéractive
-  permettre aux utilisateurs de réserver une table dans un restaurant
-  afficher la météo en temps réel à Nancy

## Base de données  
Nous utilisons une base de données oracle pour stocker et gérer les données des restaurants. Pour y accéder, nous utilisons le langage Java ainsi que l'api jdbc.  
