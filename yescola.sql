-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  jeu. 25 avr. 2019 à 12:23
-- Version du serveur :  10.1.31-MariaDB
-- Version de PHP :  7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `yescola`
--

-- --------------------------------------------------------

--
-- Structure de la table `appro`
--

CREATE TABLE `appro` (
  `id` bigint(20) NOT NULL,
  `qte_liv` varchar(255) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `bon_livraison_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

CREATE TABLE `article` (
  `id` bigint(20) NOT NULL,
  `nomarticle` varchar(255) DEFAULT NULL,
  `numeroarticle` varchar(255) DEFAULT NULL,
  `qte_stock` varchar(255) DEFAULT NULL,
  `qte_seuil` varchar(255) DEFAULT NULL,
  `pack` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `prix` varchar(255) DEFAULT NULL,
  `parfum_id` bigint(20) DEFAULT NULL,
  `depot_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `bon_livraison`
--

CREATE TABLE `bon_livraison` (
  `id` bigint(20) NOT NULL,
  `date_bl` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `boutique`
--

CREATE TABLE `boutique` (
  `id` bigint(20) NOT NULL,
  `nom_boutique` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `civilite` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `telproprietaire` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `altitude` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `periodicite` varchar(255) DEFAULT NULL,
  `date_dernier_com` varchar(255) DEFAULT NULL,
  `date_dernier_liv` varchar(255) DEFAULT NULL,
  `solde` varchar(255) DEFAULT NULL,
  `metier_id` bigint(20) DEFAULT NULL,
  `qualite_id` bigint(20) DEFAULT NULL,
  `secteur_id` bigint(20) DEFAULT NULL,
  `prospection_id` bigint(20) DEFAULT NULL,
  `commande_en_cours` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `boutique_route`
--

CREATE TABLE `boutique_route` (
  `route_id` bigint(20) NOT NULL,
  `boutique_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id` bigint(20) NOT NULL,
  `num_com` varchar(255) DEFAULT NULL,
  `date_com` date DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `montant_liv` varchar(255) DEFAULT NULL,
  `montant_rest` varchar(255) DEFAULT NULL,
  `boutique_id` bigint(20) DEFAULT NULL,
  `detail_route_id` bigint(20) DEFAULT NULL,
  `secteur_id` bigint(20) DEFAULT NULL,
  `livreur_id` bigint(20) DEFAULT NULL,
  `prevendeur_id` bigint(20) DEFAULT NULL,
  `prospection_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `commune`
--

CREATE TABLE `commune` (
  `id` bigint(20) NOT NULL,
  `nom_com` varchar(255) DEFAULT NULL,
  `longitutde` varchar(255) DEFAULT NULL,
  `altitude` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `databasechangelog`
--

CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `databasechangelog`
--

INSERT INTO `databasechangelog` (`ID`, `AUTHOR`, `FILENAME`, `DATEEXECUTED`, `ORDEREXECUTED`, `EXECTYPE`, `MD5SUM`, `DESCRIPTION`, `COMMENTS`, `TAG`, `LIQUIBASE`, `CONTEXTS`, `LABELS`, `DEPLOYMENT_ID`) VALUES
('00000000000001', 'jhipster', 'config/liquibase/changelog/00000000000000_initial_schema.xml', '2019-04-20 13:47:12', 1, 'EXECUTED', '7:5379de4cea21b8709237ca094b44210b', 'createTable tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableName=jhi_user_authority; addForeignKeyConstraint baseTableName=jhi_user_authority, constraintName=fk_authority_name, ...', '', NULL, '3.5.4', NULL, NULL, '5768031234'),
('20190420135139-1', 'jhipster', 'config/liquibase/changelog/20190420135139_added_entity_Prospection.xml', '2019-04-20 13:53:30', 2, 'EXECUTED', '7:24fe6e066a8076d03dbd73fb7489ab7c', 'createTable tableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135140-1', 'jhipster', 'config/liquibase/changelog/20190420135140_added_entity_Metier.xml', '2019-04-20 13:53:30', 3, 'EXECUTED', '7:e7e43410298ca81267895a9424ffc7b9', 'createTable tableName=metier', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135141-1', 'jhipster', 'config/liquibase/changelog/20190420135141_added_entity_Boutique.xml', '2019-04-20 13:53:30', 4, 'EXECUTED', '7:c0d4013977a630cef80f4d70d2abed7d', 'createTable tableName=boutique; createTable tableName=boutique_route; addPrimaryKey tableName=boutique_route', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135142-1', 'jhipster', 'config/liquibase/changelog/20190420135142_added_entity_Qualite.xml', '2019-04-20 13:53:30', 5, 'EXECUTED', '7:6870bc4b888aa32c38b3ad77cdde8544', 'createTable tableName=qualite', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135143-1', 'jhipster', 'config/liquibase/changelog/20190420135143_added_entity_Secteur.xml', '2019-04-20 13:53:30', 6, 'EXECUTED', '7:cc9facba5fd35386f0c293027b0b812e', 'createTable tableName=secteur; createTable tableName=secteur_route; addPrimaryKey tableName=secteur_route; createTable tableName=secteur_commune; addPrimaryKey tableName=secteur_commune', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135144-1', 'jhipster', 'config/liquibase/changelog/20190420135144_added_entity_Commune.xml', '2019-04-20 13:53:30', 7, 'EXECUTED', '7:0ecf433b92aedf63b786a4632b3ee747', 'createTable tableName=commune', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135145-1', 'jhipster', 'config/liquibase/changelog/20190420135145_added_entity_Commande.xml', '2019-04-20 13:53:30', 8, 'EXECUTED', '7:caf9dea54057544ab66fe254b8119834', 'createTable tableName=commande', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135146-1', 'jhipster', 'config/liquibase/changelog/20190420135146_added_entity_DetailCom.xml', '2019-04-20 13:53:30', 9, 'EXECUTED', '7:97ffbf597299daefb375fa6d5ba27732', 'createTable tableName=detail_com', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135147-1', 'jhipster', 'config/liquibase/changelog/20190420135147_added_entity_StockInitial.xml', '2019-04-20 13:53:30', 10, 'EXECUTED', '7:21333f6fcf512ec2ef581a5f809a166f', 'createTable tableName=stock_initial', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135148-1', 'jhipster', 'config/liquibase/changelog/20190420135148_added_entity_Article.xml', '2019-04-20 13:53:30', 11, 'EXECUTED', '7:4a14554af6a3320995d0d35501438d88', 'createTable tableName=article', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135149-1', 'jhipster', 'config/liquibase/changelog/20190420135149_added_entity_Parfum.xml', '2019-04-20 13:53:30', 12, 'EXECUTED', '7:1677d559d827d93b2bfadd6bbf1ab9dd', 'createTable tableName=parfum', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135150-1', 'jhipster', 'config/liquibase/changelog/20190420135150_added_entity_Tablette.xml', '2019-04-20 13:53:30', 13, 'EXECUTED', '7:9babde67eef3f6405095b631afd4cc46', 'createTable tableName=tablette', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135151-1', 'jhipster', 'config/liquibase/changelog/20190420135151_added_entity_Depot.xml', '2019-04-20 13:53:30', 14, 'EXECUTED', '7:b54ae16b0b58a9cf462308a2186b1734', 'createTable tableName=depot', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135152-1', 'jhipster', 'config/liquibase/changelog/20190420135152_added_entity_Route.xml', '2019-04-20 13:53:30', 15, 'EXECUTED', '7:e0f0256b7c95e158bccb72902afd787b', 'createTable tableName=route', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135153-1', 'jhipster', 'config/liquibase/changelog/20190420135153_added_entity_TypeRoute.xml', '2019-04-20 13:53:31', 16, 'EXECUTED', '7:5988d8390702513b25354aee471e42b4', 'createTable tableName=type_route', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135154-1', 'jhipster', 'config/liquibase/changelog/20190420135154_added_entity_DetailRoute.xml', '2019-04-20 13:53:31', 17, 'EXECUTED', '7:dea563f5b830b3524f95292b96614fbd', 'createTable tableName=detail_route', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135155-1', 'jhipster', 'config/liquibase/changelog/20190420135155_added_entity_Reglement.xml', '2019-04-20 13:53:31', 18, 'EXECUTED', '7:d0843426091344f0e9ce66e756baff4b', 'createTable tableName=reglement', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135156-1', 'jhipster', 'config/liquibase/changelog/20190420135156_added_entity_TypeRglment.xml', '2019-04-20 13:53:31', 19, 'EXECUTED', '7:81f374ec708e97a532d80105b207e4fc', 'createTable tableName=type_rglment', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135157-1', 'jhipster', 'config/liquibase/changelog/20190420135157_added_entity_MoyenTransport.xml', '2019-04-20 13:53:31', 20, 'EXECUTED', '7:005b08eea0f39b3fc76ad57cea58c3ab', 'createTable tableName=moyen_transport', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135158-1', 'jhipster', 'config/liquibase/changelog/20190420135158_added_entity_TypeTransport.xml', '2019-04-20 13:53:31', 21, 'EXECUTED', '7:93d691b0c76827087b19e76d224d41dd', 'createTable tableName=type_transport', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135139-2', 'jhipster', 'config/liquibase/changelog/20190420135139_added_entity_constraints_Prospection.xml', '2019-04-20 13:53:31', 22, 'EXECUTED', '7:7e65654b79a79585b31cccaf775c88d7', 'addForeignKeyConstraint baseTableName=prospection, constraintName=fk_prospection_user_id, referencedTableName=jhi_user; addForeignKeyConstraint baseTableName=prospection, constraintName=fk_prospection_moyen_transport_id, referencedTableName=moyen_...', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135141-2', 'jhipster', 'config/liquibase/changelog/20190420135141_added_entity_constraints_Boutique.xml', '2019-04-20 13:53:31', 23, 'EXECUTED', '7:28641c8660bb0bad4ab9fa0e33dd5ac0', 'addForeignKeyConstraint baseTableName=boutique, constraintName=fk_boutique_metier_id, referencedTableName=metier; addForeignKeyConstraint baseTableName=boutique, constraintName=fk_boutique_qualite_id, referencedTableName=qualite; addForeignKeyCons...', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135143-2', 'jhipster', 'config/liquibase/changelog/20190420135143_added_entity_constraints_Secteur.xml', '2019-04-20 13:53:32', 24, 'EXECUTED', '7:e9718fb39fe563a2309c0bbd5a1d01ea', 'addForeignKeyConstraint baseTableName=secteur_route, constraintName=fk_secteur_route_secteur_id, referencedTableName=secteur; addForeignKeyConstraint baseTableName=secteur_route, constraintName=fk_secteur_route_route_id, referencedTableName=route;...', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135145-2', 'jhipster', 'config/liquibase/changelog/20190420135145_added_entity_constraints_Commande.xml', '2019-04-20 13:53:32', 25, 'EXECUTED', '7:ba89db3c2723b7b380770b53504d1208', 'addForeignKeyConstraint baseTableName=commande, constraintName=fk_commande_boutique_id, referencedTableName=boutique; addForeignKeyConstraint baseTableName=commande, constraintName=fk_commande_detail_route_id, referencedTableName=detail_route; add...', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135146-2', 'jhipster', 'config/liquibase/changelog/20190420135146_added_entity_constraints_DetailCom.xml', '2019-04-20 13:53:32', 26, 'EXECUTED', '7:8833473d36c8524ddc8f2a48059f8378', 'addForeignKeyConstraint baseTableName=detail_com, constraintName=fk_detail_com_article_id, referencedTableName=article; addForeignKeyConstraint baseTableName=detail_com, constraintName=fk_detail_com_commande_id, referencedTableName=commande', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135147-2', 'jhipster', 'config/liquibase/changelog/20190420135147_added_entity_constraints_StockInitial.xml', '2019-04-20 13:53:33', 27, 'EXECUTED', '7:fe4667b4cdc95409e434733e3536fc1a', 'addForeignKeyConstraint baseTableName=stock_initial, constraintName=fk_stock_initial_boutique_id, referencedTableName=boutique; addForeignKeyConstraint baseTableName=stock_initial, constraintName=fk_stock_initial_article_id, referencedTableName=ar...', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135148-2', 'jhipster', 'config/liquibase/changelog/20190420135148_added_entity_constraints_Article.xml', '2019-04-20 13:53:33', 28, 'EXECUTED', '7:9681d5c61475d9171f5c0d15eb89f7ce', 'addForeignKeyConstraint baseTableName=article, constraintName=fk_article_parfum_id, referencedTableName=parfum; addForeignKeyConstraint baseTableName=article, constraintName=fk_article_depot_id, referencedTableName=depot', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135150-2', 'jhipster', 'config/liquibase/changelog/20190420135150_added_entity_constraints_Tablette.xml', '2019-04-20 13:53:33', 29, 'EXECUTED', '7:9829e662ce1c35ced30420fd0e2563f2', 'addForeignKeyConstraint baseTableName=tablette, constraintName=fk_tablette_user_id, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135152-2', 'jhipster', 'config/liquibase/changelog/20190420135152_added_entity_constraints_Route.xml', '2019-04-20 13:53:33', 30, 'EXECUTED', '7:684c930873c65d81df0112bd7c9784a2', 'addForeignKeyConstraint baseTableName=route, constraintName=fk_route_moyen_transport_id, referencedTableName=moyen_transport; addForeignKeyConstraint baseTableName=route, constraintName=fk_route_type_route_id, referencedTableName=type_route; addFo...', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135154-2', 'jhipster', 'config/liquibase/changelog/20190420135154_added_entity_constraints_DetailRoute.xml', '2019-04-20 13:53:33', 31, 'EXECUTED', '7:a2f1a5e1967f7c02c2b8f69027c788fd', 'addForeignKeyConstraint baseTableName=detail_route, constraintName=fk_detail_route_route_id, referencedTableName=route; addForeignKeyConstraint baseTableName=detail_route, constraintName=fk_detail_route_boutique_id, referencedTableName=boutique', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135155-2', 'jhipster', 'config/liquibase/changelog/20190420135155_added_entity_constraints_Reglement.xml', '2019-04-20 13:53:34', 32, 'EXECUTED', '7:f20f2c4b85ea4e7e2dbda17a4f597b11', 'addForeignKeyConstraint baseTableName=reglement, constraintName=fk_reglement_type_rglment_id, referencedTableName=type_rglment; addForeignKeyConstraint baseTableName=reglement, constraintName=fk_reglement_commande_id, referencedTableName=commande', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('20190420135157-2', 'jhipster', 'config/liquibase/changelog/20190420135157_added_entity_constraints_MoyenTransport.xml', '2019-04-20 13:53:34', 33, 'EXECUTED', '7:1021e02ffb38cf2aa74169823c1f583e', 'addForeignKeyConstraint baseTableName=moyen_transport, constraintName=fk_moyen_transport_type_transport_id, referencedTableName=type_transport', '', NULL, '3.5.4', NULL, NULL, '5768409871'),
('1555771846246-1', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:11', 34, 'EXECUTED', '7:a0c920c95b013ee31a0e274cd9bf999f', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-2', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 35, 'EXECUTED', '7:c99b368b6747994a7e7c90080e0d8fe2', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-3', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 36, 'EXECUTED', '7:40c5cce61443c12b1042296265b032e9', 'addColumn tableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-4', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 37, 'EXECUTED', '7:2bdb2252cfe7086c099d528887020b42', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-5', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 38, 'EXECUTED', '7:f259c669c981eebde8fcbe31a845e28a', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-6', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 39, 'EXECUTED', '7:3c6ebf7318d31723bf85e8d835c95d89', 'addColumn tableName=commande', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-7', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 40, 'EXECUTED', '7:eb89c15451bc6a58fb9e3a63778c1116', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-8', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 41, 'EXECUTED', '7:7b8f59932fa6f19e020b240c004fa7a4', 'addColumn tableName=commande', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-9', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 42, 'EXECUTED', '7:c6e79032bd8abe9445c53a89370e904b', 'addColumn tableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-10', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 43, 'EXECUTED', '7:c8312c88f26ed89671e1a858eae012ac', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-11', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 44, 'EXECUTED', '7:f8e64109fee534f147d25c05fdc4902e', 'addColumn tableName=commande', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-12', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 45, 'EXECUTED', '7:0a7d889e29e15232b3b40a0d949607a7', 'addForeignKeyConstraint baseTableName=commande, constraintName=FK3us06dgnicn260gcf7fvg1kp0, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-13', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 46, 'EXECUTED', '7:0b28c33923fd81692e3cc15148b2516c', 'addForeignKeyConstraint baseTableName=route, constraintName=FK3wypjhanwusoqm4ofpu0inyhq, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-14', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:12', 47, 'EXECUTED', '7:5e0171d349fa1fdb040364180743b08f', 'addForeignKeyConstraint baseTableName=route, constraintName=FK47ekk7k92s4g40pr6jmmyelv3, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-15', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:13', 48, 'EXECUTED', '7:7782a5aabd67d6eb609488b43048fef2', 'addForeignKeyConstraint baseTableName=route, constraintName=FK4enuoq6vtqv6f2tc02c16o12o, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-16', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:13', 49, 'EXECUTED', '7:0bf74f82db7b77e3aea65c33dcab0c0f', 'addForeignKeyConstraint baseTableName=route, constraintName=FK8lp2yq20ybq8xm1whsgk0lg7k, referencedTableName=type_route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-17', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:13', 50, 'EXECUTED', '7:424b015b96a83334bfcce1d9ed6ccefb', 'addForeignKeyConstraint baseTableName=prospection, constraintName=FKgg24vw24ghknvn45swhrsxugg, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-18', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:13', 51, 'EXECUTED', '7:4abe8de7d7cbafee2143c13d0df9ebdc', 'addForeignKeyConstraint baseTableName=commande, constraintName=FKjosyyfgn23unau6jr5orlf3hj, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-19', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:13', 52, 'EXECUTED', '7:039011288029e8019ad246235b3a5e6d', 'addForeignKeyConstraint baseTableName=route, constraintName=FKkvbpthqr7hxsx2qe637onekyv, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-20', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:13', 53, 'EXECUTED', '7:d8e3201e12ab26bfc6cb7cf51817a3bc', 'addForeignKeyConstraint baseTableName=commande, constraintName=FKldtjtj41nkdukep1x6b498fsj, referencedTableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-21', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:13', 54, 'EXECUTED', '7:1634fef4e3450089cb7d7ffb2175d6f6', 'addForeignKeyConstraint baseTableName=route, constraintName=FKq9leed88at43a7nlaacjql164, referencedTableName=type_route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-22', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:14', 55, 'EXECUTED', '7:2e65ff8b698d9c8d2ec080348d327d58', 'addForeignKeyConstraint baseTableName=prospection, constraintName=FKtrqwmj9ctw9hddk3rr0ucbbja, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-23', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:14', 56, 'EXECUTED', '7:b8c0cacf3591eb80ce8812e9c25e938c', 'dropForeignKeyConstraint baseTableName=commande, constraintName=fk_commande_user_id', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-24', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:14', 57, 'EXECUTED', '7:323a803144e1ba9788a9791e79082e4c', 'dropForeignKeyConstraint baseTableName=prospection, constraintName=fk_prospection_user_id', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-25', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:14', 58, 'EXECUTED', '7:9fc607f3d8ed29198ec7d7ba35d46847', 'dropForeignKeyConstraint baseTableName=route, constraintName=fk_route_type_route_id', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-26', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:14', 59, 'EXECUTED', '7:0754237a9e913e6805b1f8fe57fbf719', 'dropForeignKeyConstraint baseTableName=route, constraintName=fk_route_user_id', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-27', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:14', 60, 'EXECUTED', '7:c888381ba9459bf4235193455e5d0a7f', 'dropColumn columnName=type_route_id, tableName=route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-28', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:14', 61, 'EXECUTED', '7:35bfadf498d82c7854514069d0f0f3e4', 'dropColumn columnName=user_id, tableName=commande', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-29', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:14', 62, 'EXECUTED', '7:9f552c9f87032030ad8c0e0c8f98aed3', 'dropColumn columnName=user_id, tableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('1555771846246-30', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190420145019_changelog.xml', '2019-04-20 15:04:14', 63, 'EXECUTED', '7:a441f226136ebd7fc7c286660cabdaa5', 'dropColumn columnName=user_id, tableName=route', '', NULL, '3.5.4', NULL, NULL, '5772651885'),
('20190421214051-1', 'jhipster', 'config/liquibase/changelog/20190421214051_added_entity_Employee.xml', '2019-04-21 21:43:48', 64, 'EXECUTED', '7:ca5ae147834d01037a0fe5f17c72a33e', 'createTable tableName=employee', '', NULL, '3.5.4', NULL, NULL, '5883028191'),
('20190421214051-2', 'jhipster', 'config/liquibase/changelog/20190421214051_added_entity_constraints_Employee.xml', '2019-04-21 21:43:48', 65, 'EXECUTED', '7:f9f898b152153b70b438b461c788bc64', 'addForeignKeyConstraint baseTableName=employee, constraintName=fk_employee_user_id, referencedTableName=jhi_user', '', NULL, '3.5.4', NULL, NULL, '5883028191'),
('1555884714831-1', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 66, 'EXECUTED', '7:a91f4493dd92b034e5caa516b5da69a2', 'addColumn tableName=boutique', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-2', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 67, 'EXECUTED', '7:6bcb4de5184eb563b2350579e8dfe72d', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-3', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 68, 'EXECUTED', '7:8a340b1adbb6f53f5b336fb9241a6f4a', 'addColumn tableName=moyen_transport', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-4', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 69, 'EXECUTED', '7:64f566bcbbffb674e00c87503b2885bd', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-5', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 70, 'EXECUTED', '7:cc115bf46d7c2b0c70c86228dad27045', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-6', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 71, 'EXECUTED', '7:5f9caa48d1fb401ed339a3f1a6acd9e2', 'addColumn tableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-7', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 72, 'EXECUTED', '7:192a70a2cc32f775762ffdebdcd3adba', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-8', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 73, 'EXECUTED', '7:062f1da9c660236533d852aaf6e2e8c1', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-9', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 74, 'EXECUTED', '7:d14cffd8c70916d8902cc104b66e18ee', 'addColumn tableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-10', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:39', 75, 'EXECUTED', '7:25b73fb7aa26f2b017090f0188e6c5f1', 'addColumn tableName=tablette', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-11', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:40', 76, 'EXECUTED', '7:8dd8d68b17cecc82bead0083ee5fce6c', 'addColumn tableName=route', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-12', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:40', 77, 'EXECUTED', '7:508754f4e0c8a13e9edb5e7f1ff9e5e9', 'addColumn tableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-13', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:40', 78, 'EXECUTED', '7:5f1a399e282a1599f8e07bf26d35a213', 'addColumn tableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-14', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:40', 79, 'EXECUTED', '7:76394299acab0bb15e6f8f0e61770003', 'addColumn tableName=tablette', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-15', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:40', 80, 'EXECUTED', '7:b4c89f58cdc7631ac94f697e7625086e', 'addUniqueConstraint constraintName=UC_TABLETTEEMPLOYEE_ID_COL, tableName=tablette', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-16', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:40', 81, 'EXECUTED', '7:b2730210dad2d9a22efc1fc09de00f5f', 'addForeignKeyConstraint baseTableName=commande, constraintName=FK19h4r9hnt3pjwlrn7xk68ifmc, referencedTableName=employee', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-17', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:40', 82, 'EXECUTED', '7:b1a4470002376375c54bdb6bebafac6b', 'addForeignKeyConstraint baseTableName=tablette, constraintName=FK20u4xyim0avdhx4awg3gdlqkc, referencedTableName=employee', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-18', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:40', 83, 'EXECUTED', '7:b658acd17490470d093fe1dc4d335a74', 'addForeignKeyConstraint baseTableName=prospection, constraintName=FK99b577cidr56lywx6h3mo4q70, referencedTableName=employee', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-19', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 84, 'EXECUTED', '7:730258cf748986114b69fcdcc7a806f1', 'addForeignKeyConstraint baseTableName=route, constraintName=FKhsvugeqkd24oeujj9wc4a55gd, referencedTableName=employee', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-20', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 85, 'EXECUTED', '7:a80a48e087e323b4c6b522f9f21dfdd3', 'addForeignKeyConstraint baseTableName=route, constraintName=FKhtob5dfg8haa51r9w0qr0q0ei, referencedTableName=employee', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-21', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 86, 'EXECUTED', '7:d092bad3845072a8df709ebd1b42330b', 'addForeignKeyConstraint baseTableName=route, constraintName=FKon5ebhxppvuod2p0709ompqaa, referencedTableName=employee', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-22', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 87, 'EXECUTED', '7:f8da1e71551c779054771607e8a2b673', 'addForeignKeyConstraint baseTableName=commande, constraintName=FKplbyqofyoknimfxc0hqiecj5n, referencedTableName=employee', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-23', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 88, 'EXECUTED', '7:0332f3da5a88fbf34ab8938fa09c2337', 'addForeignKeyConstraint baseTableName=prospection, constraintName=FKsf5lyx65cpv7urxqw0gvi8m6s, referencedTableName=employee', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-24', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 89, 'EXECUTED', '7:2268b482a228070ab78e5bf56457ab9b', 'dropForeignKeyConstraint baseTableName=commande, constraintName=FK3us06dgnicn260gcf7fvg1kp0', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-25', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 90, 'EXECUTED', '7:fa1ca665ab4082198736224547e94940', 'dropForeignKeyConstraint baseTableName=route, constraintName=FK3wypjhanwusoqm4ofpu0inyhq', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-26', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 91, 'EXECUTED', '7:9a5cb9ded366d01abbb7f11ea2f10ec1', 'dropForeignKeyConstraint baseTableName=route, constraintName=FK47ekk7k92s4g40pr6jmmyelv3', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-27', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 92, 'EXECUTED', '7:a6c7c963eb44982e62d2dbc5faf66d6d', 'dropForeignKeyConstraint baseTableName=route, constraintName=FK4enuoq6vtqv6f2tc02c16o12o', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-28', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 93, 'EXECUTED', '7:467271f53a2336752b6d3927f82b190e', 'dropForeignKeyConstraint baseTableName=prospection, constraintName=FKgg24vw24ghknvn45swhrsxugg', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-29', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 94, 'EXECUTED', '7:001ae68ff6566ccba6ba3159b9805fd9', 'dropForeignKeyConstraint baseTableName=commande, constraintName=FKjosyyfgn23unau6jr5orlf3hj', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-30', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 95, 'EXECUTED', '7:d619e118c05b1e97f846a7c1e9528cb2', 'dropForeignKeyConstraint baseTableName=prospection, constraintName=FKtrqwmj9ctw9hddk3rr0ucbbja', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-31', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:41', 96, 'EXECUTED', '7:f7646a23d5e0a38882217cd5f7a8dc85', 'dropForeignKeyConstraint baseTableName=tablette, constraintName=fk_tablette_user_id', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-32', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:42', 97, 'EXECUTED', '7:14428149ede6a133522796d669f73c8d', 'dropUniqueConstraint constraintName=ux_tablette_user_id, tableName=tablette', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-33', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:42', 98, 'EXECUTED', '7:329f7e47f8e783815c5c6eef04126045', 'dropColumn columnName=date_a_list, tableName=moyen_transport', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-34', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:42', 99, 'EXECUTED', '7:0046151fabf03134627ee08aa7372dcd', 'dropColumn columnName=date_liv, tableName=route', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-35', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:42', 100, 'EXECUTED', '7:7b11a1bd50b7a50cfd5483d0cefbe5ea', 'dropColumn columnName=dateprospec, tableName=prospection', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-36', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:42', 101, 'EXECUTED', '7:1c0d91285b192071b3500b43519b334b', 'dropColumn columnName=gerentlivraison_id, tableName=route', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-37', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:42', 102, 'EXECUTED', '7:675024a253d67a1dff49a4a7b73aba6b', 'dropColumn columnName=jhi_date, tableName=route', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('1555884714831-38', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190421221131_changelog.xml', '2019-04-21 22:14:42', 103, 'EXECUTED', '7:8e6a3d7545849b4c2a8c8e79310e5578', 'dropColumn columnName=user_id, tableName=tablette', '', NULL, '3.5.4', NULL, NULL, '5884878993'),
('20190424132035-1', 'jhipster', 'config/liquibase/changelog/20190424132035_added_entity_BonLivraison.xml', '2019-04-24 13:25:11', 104, 'EXECUTED', '7:cb10c26192f51cca0a3a974ef5b9f8bb', 'createTable tableName=bon_livraison', '', NULL, '3.5.4', NULL, NULL, '6112311223'),
('20190424132036-1', 'jhipster', 'config/liquibase/changelog/20190424132036_added_entity_Appro.xml', '2019-04-24 13:25:11', 105, 'EXECUTED', '7:62cfc50650855fca36bb13818258a8e8', 'createTable tableName=appro', '', NULL, '3.5.4', NULL, NULL, '6112311223'),
('20190424132036-2', 'jhipster', 'config/liquibase/changelog/20190424132036_added_entity_constraints_Appro.xml', '2019-04-24 13:25:11', 106, 'EXECUTED', '7:f9128fae361862a4587f51559a205573', 'addForeignKeyConstraint baseTableName=appro, constraintName=fk_appro_article_id, referencedTableName=article; addForeignKeyConstraint baseTableName=appro, constraintName=fk_appro_bon_livraison_id, referencedTableName=bon_livraison', '', NULL, '3.5.4', NULL, NULL, '6112311223'),
('1556113216073-1', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190424133953_changelog.xml', '2019-04-24 13:41:12', 107, 'EXECUTED', '7:e0dbbaade92d1aabc100dae2572f0154', 'addColumn tableName=employee', '', NULL, '3.5.4', NULL, NULL, '6113272044'),
('1556113216073-2', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190424133953_changelog.xml', '2019-04-24 13:41:12', 108, 'EXECUTED', '7:0cdd08c21be5d5e9c640153c161f112e', 'addUniqueConstraint constraintName=UC_EMPLOYEETABLETTE_ID_COL, tableName=employee', '', NULL, '3.5.4', NULL, NULL, '6113272044'),
('1556113216073-3', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190424133953_changelog.xml', '2019-04-24 13:41:12', 109, 'EXECUTED', '7:fb3747a9003987aa66a5483e1d95c8c7', 'addForeignKeyConstraint baseTableName=employee, constraintName=FK2uuwg9bodewqilxjxfu6mfxn, referencedTableName=tablette', '', NULL, '3.5.4', NULL, NULL, '6113272044'),
('1556113216073-4', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190424133953_changelog.xml', '2019-04-24 13:41:12', 110, 'EXECUTED', '7:7c4268de37232d3c5122d97d2140f8c3', 'dropForeignKeyConstraint baseTableName=tablette, constraintName=FK20u4xyim0avdhx4awg3gdlqkc', '', NULL, '3.5.4', NULL, NULL, '6113272044'),
('1556113216073-5', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190424133953_changelog.xml', '2019-04-24 13:41:12', 111, 'EXECUTED', '7:4a06d540cbc31beb07dbaa4f5892d63a', 'dropUniqueConstraint constraintName=UC_TABLETTEEMPLOYEE_ID_COL, tableName=tablette', '', NULL, '3.5.4', NULL, NULL, '6113272044'),
('1556113216073-6', 'Makhmadanelo (generated)', 'config/liquibase/changelog/20190424133953_changelog.xml', '2019-04-24 13:41:12', 112, 'EXECUTED', '7:972001b6814de2fc247751b38e7ef335', 'dropColumn columnName=employee_id, tableName=tablette', '', NULL, '3.5.4', NULL, NULL, '6113272044');

-- --------------------------------------------------------

--
-- Structure de la table `databasechangeloglock`
--

CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `databasechangeloglock`
--

INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, b'0', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `depot`
--

CREATE TABLE `depot` (
  `id` bigint(20) NOT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `altitude` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `detail_com`
--

CREATE TABLE `detail_com` (
  `id` bigint(20) NOT NULL,
  `qte_com` varchar(255) DEFAULT NULL,
  `qte_liv` varchar(255) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL,
  `commande_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `detail_route`
--

CREATE TABLE `detail_route` (
  `id` bigint(20) NOT NULL,
  `heure_a_com` varchar(255) DEFAULT NULL,
  `heure_f_com` varchar(255) DEFAULT NULL,
  `heure_a_liv` varchar(255) DEFAULT NULL,
  `heure_f_liv` varchar(255) DEFAULT NULL,
  `distance_depot` varchar(255) DEFAULT NULL,
  `route_id` bigint(20) DEFAULT NULL,
  `boutique_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `employee`
--

CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL,
  `nomcomplet` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `tablette_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `jhi_authority`
--

CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `jhi_authority`
--

INSERT INTO `jhi_authority` (`name`) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- --------------------------------------------------------

--
-- Structure de la table `jhi_persistent_audit_event`
--

CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `jhi_persistent_audit_event`
--

INSERT INTO `jhi_persistent_audit_event` (`event_id`, `principal`, `event_date`, `event_type`) VALUES
(1, 'admin', '2019-04-20 13:49:02', 'AUTHENTICATION_SUCCESS'),
(2, 'admin', '2019-04-20 13:49:02', 'AUTHENTICATION_SUCCESS'),
(3, 'admin', '2019-04-20 13:49:03', 'AUTHENTICATION_SUCCESS'),
(4, 'admin', '2019-04-20 13:49:27', 'AUTHENTICATION_SUCCESS'),
(5, 'admin', '2019-04-20 13:54:33', 'AUTHENTICATION_SUCCESS'),
(6, 'admin', '2019-04-20 14:32:16', 'AUTHENTICATION_SUCCESS'),
(7, 'admin', '2019-04-20 14:34:57', 'AUTHENTICATION_SUCCESS'),
(8, 'admin', '2019-04-20 14:37:38', 'AUTHENTICATION_SUCCESS'),
(9, 'admin', '2019-04-20 14:50:45', 'AUTHENTICATION_SUCCESS'),
(10, 'admin', '2019-04-20 14:53:14', 'AUTHENTICATION_SUCCESS'),
(11, 'admin', '2019-04-20 15:06:12', 'AUTHENTICATION_SUCCESS'),
(12, 'admin', '2019-04-21 18:42:22', 'AUTHENTICATION_SUCCESS'),
(13, 'admin', '2019-04-21 18:44:04', 'AUTHENTICATION_SUCCESS'),
(14, 'admin', '2019-04-21 18:45:13', 'AUTHENTICATION_SUCCESS'),
(15, 'admin', '2019-04-21 18:46:04', 'AUTHENTICATION_SUCCESS'),
(16, 'admin', '2019-04-21 18:47:02', 'AUTHENTICATION_SUCCESS'),
(17, 'admin', '2019-04-21 18:48:12', 'AUTHENTICATION_SUCCESS'),
(18, 'admin', '2019-04-21 18:52:15', 'AUTHENTICATION_SUCCESS'),
(19, 'admin', '2019-04-21 18:55:17', 'AUTHENTICATION_SUCCESS'),
(20, 'admin', '2019-04-21 19:01:57', 'AUTHENTICATION_SUCCESS'),
(21, 'admin', '2019-04-21 19:02:06', 'AUTHENTICATION_SUCCESS'),
(22, 'admin', '2019-04-21 19:03:58', 'AUTHENTICATION_SUCCESS'),
(23, 'admin', '2019-04-21 19:05:57', 'AUTHENTICATION_SUCCESS'),
(24, 'admin', '2019-04-21 19:07:15', 'AUTHENTICATION_SUCCESS'),
(25, 'admin', '2019-04-21 19:07:27', 'AUTHENTICATION_SUCCESS'),
(26, 'admin', '2019-04-21 19:08:41', 'AUTHENTICATION_SUCCESS'),
(27, 'admin', '2019-04-21 19:10:14', 'AUTHENTICATION_SUCCESS'),
(28, 'admin', '2019-04-21 19:12:00', 'AUTHENTICATION_SUCCESS'),
(29, 'admin', '2019-04-21 19:14:30', 'AUTHENTICATION_SUCCESS'),
(30, 'admin', '2019-04-21 19:19:52', 'AUTHENTICATION_SUCCESS'),
(31, 'admin', '2019-04-21 19:22:43', 'AUTHENTICATION_SUCCESS'),
(32, 'admin', '2019-04-21 19:22:48', 'AUTHENTICATION_SUCCESS'),
(33, 'admin', '2019-04-21 19:23:45', 'AUTHENTICATION_SUCCESS'),
(34, 'admin', '2019-04-21 19:25:09', 'AUTHENTICATION_SUCCESS'),
(35, 'admin', '2019-04-21 19:28:39', 'AUTHENTICATION_SUCCESS'),
(36, 'admin', '2019-04-21 19:29:56', 'AUTHENTICATION_SUCCESS'),
(37, 'admin', '2019-04-21 19:30:05', 'AUTHENTICATION_SUCCESS'),
(38, 'admin', '2019-04-21 19:30:25', 'AUTHENTICATION_SUCCESS'),
(39, 'admin', '2019-04-21 19:33:21', 'AUTHENTICATION_SUCCESS'),
(40, 'admin', '2019-04-21 19:33:41', 'AUTHENTICATION_SUCCESS'),
(41, 'admin', '2019-04-21 19:40:31', 'AUTHENTICATION_SUCCESS'),
(42, 'admin', '2019-04-21 19:40:34', 'AUTHENTICATION_SUCCESS'),
(43, 'admin', '2019-04-21 19:41:02', 'AUTHENTICATION_SUCCESS'),
(44, 'admin', '2019-04-21 19:45:06', 'AUTHENTICATION_SUCCESS'),
(45, 'admin', '2019-04-21 19:49:31', 'AUTHENTICATION_SUCCESS'),
(46, 'admin', '2019-04-21 19:53:36', 'AUTHENTICATION_SUCCESS'),
(47, 'admin', '2019-04-21 19:55:11', 'AUTHENTICATION_SUCCESS'),
(48, 'admin', '2019-04-21 20:01:06', 'AUTHENTICATION_SUCCESS'),
(49, 'admin', '2019-04-21 20:01:26', 'AUTHENTICATION_SUCCESS'),
(50, 'admin', '2019-04-21 20:08:32', 'AUTHENTICATION_SUCCESS'),
(51, 'admin', '2019-04-21 20:08:34', 'AUTHENTICATION_SUCCESS'),
(52, 'admin', '2019-04-21 20:10:17', 'AUTHENTICATION_SUCCESS'),
(53, 'admin', '2019-04-21 20:11:23', 'AUTHENTICATION_SUCCESS'),
(54, 'admin', '2019-04-21 20:11:51', 'AUTHENTICATION_SUCCESS'),
(55, 'admin', '2019-04-21 20:15:08', 'AUTHENTICATION_SUCCESS'),
(56, 'admin', '2019-04-21 20:15:11', 'AUTHENTICATION_SUCCESS'),
(57, 'admin', '2019-04-21 20:21:34', 'AUTHENTICATION_SUCCESS'),
(58, 'admin', '2019-04-21 20:21:45', 'AUTHENTICATION_SUCCESS'),
(59, 'admin', '2019-04-21 20:23:14', 'AUTHENTICATION_SUCCESS'),
(60, 'admin', '2019-04-21 20:23:25', 'AUTHENTICATION_SUCCESS'),
(61, 'admin', '2019-04-21 20:25:41', 'AUTHENTICATION_SUCCESS'),
(62, 'admin', '2019-04-21 20:26:34', 'AUTHENTICATION_SUCCESS'),
(63, 'admin', '2019-04-21 20:26:41', 'AUTHENTICATION_SUCCESS'),
(64, 'admin', '2019-04-21 20:35:49', 'AUTHENTICATION_SUCCESS'),
(65, 'admin', '2019-04-21 20:38:06', 'AUTHENTICATION_SUCCESS'),
(66, 'admin', '2019-04-21 20:41:34', 'AUTHENTICATION_SUCCESS'),
(67, 'admin', '2019-04-21 20:43:25', 'AUTHENTICATION_SUCCESS'),
(68, 'admin', '2019-04-21 20:43:36', 'AUTHENTICATION_SUCCESS'),
(69, 'admin', '2019-04-21 20:44:06', 'AUTHENTICATION_SUCCESS'),
(70, 'admin', '2019-04-21 21:11:08', 'AUTHENTICATION_SUCCESS'),
(71, 'admin', '2019-04-21 21:12:00', 'AUTHENTICATION_SUCCESS'),
(72, 'admin', '2019-04-21 21:12:54', 'AUTHENTICATION_SUCCESS'),
(73, 'admin', '2019-04-21 21:15:45', 'AUTHENTICATION_SUCCESS'),
(74, 'admin', '2019-04-21 21:17:42', 'AUTHENTICATION_SUCCESS'),
(75, 'admin', '2019-04-21 21:19:19', 'AUTHENTICATION_SUCCESS'),
(76, 'admin', '2019-04-21 21:20:47', 'AUTHENTICATION_SUCCESS'),
(77, 'admin', '2019-04-21 21:22:07', 'AUTHENTICATION_SUCCESS'),
(78, 'admin', '2019-04-21 21:24:18', 'AUTHENTICATION_SUCCESS'),
(79, 'admin', '2019-04-21 21:27:00', 'AUTHENTICATION_SUCCESS'),
(80, 'admin', '2019-04-21 21:32:11', 'AUTHENTICATION_SUCCESS'),
(81, 'admin', '2019-04-21 21:34:23', 'AUTHENTICATION_SUCCESS'),
(82, 'admin', '2019-04-21 22:04:11', 'AUTHENTICATION_SUCCESS'),
(83, 'admin', '2019-04-21 22:06:05', 'AUTHENTICATION_SUCCESS'),
(84, 'admin', '2019-04-21 22:09:40', 'AUTHENTICATION_SUCCESS'),
(85, 'admin', '2019-04-21 22:12:51', 'AUTHENTICATION_SUCCESS'),
(86, 'admin', '2019-04-21 22:16:32', 'AUTHENTICATION_SUCCESS'),
(87, 'admin', '2019-04-21 22:28:38', 'AUTHENTICATION_SUCCESS'),
(88, 'admin', '2019-04-21 22:42:19', 'AUTHENTICATION_SUCCESS'),
(89, 'admin', '2019-04-21 22:45:26', 'AUTHENTICATION_SUCCESS'),
(90, 'admin', '2019-04-22 00:05:20', 'AUTHENTICATION_SUCCESS'),
(91, 'admin', '2019-04-22 20:32:10', 'AUTHENTICATION_SUCCESS'),
(92, 'admin', '2019-04-22 20:39:49', 'AUTHENTICATION_SUCCESS'),
(93, 'admin', '2019-04-22 20:41:40', 'AUTHENTICATION_SUCCESS'),
(94, 'admin', '2019-04-22 20:44:47', 'AUTHENTICATION_SUCCESS'),
(95, 'admin', '2019-04-22 20:44:49', 'AUTHENTICATION_SUCCESS'),
(96, 'admin', '2019-04-22 20:44:50', 'AUTHENTICATION_SUCCESS'),
(97, 'admin', '2019-04-22 20:44:52', 'AUTHENTICATION_SUCCESS'),
(98, 'admin', '2019-04-22 20:45:35', 'AUTHENTICATION_SUCCESS'),
(99, 'admin', '2019-04-22 20:45:46', 'AUTHENTICATION_SUCCESS'),
(100, 'admin', '2019-04-22 20:49:16', 'AUTHENTICATION_SUCCESS'),
(101, 'admin', '2019-04-22 20:56:23', 'AUTHENTICATION_SUCCESS'),
(102, 'admin', '2019-04-22 21:06:00', 'AUTHENTICATION_SUCCESS'),
(103, 'admin', '2019-04-22 21:08:20', 'AUTHENTICATION_SUCCESS'),
(104, 'admin', '2019-04-22 21:29:25', 'AUTHENTICATION_SUCCESS'),
(105, 'admin', '2019-04-22 21:48:04', 'AUTHENTICATION_SUCCESS'),
(106, 'admin', '2019-04-22 22:00:49', 'AUTHENTICATION_SUCCESS'),
(107, 'admin', '2019-04-23 09:18:00', 'AUTHENTICATION_SUCCESS'),
(108, 'admin', '2019-04-23 09:20:13', 'AUTHENTICATION_SUCCESS'),
(109, 'admin', '2019-04-23 10:12:21', 'AUTHENTICATION_SUCCESS'),
(110, 'admin', '2019-04-23 10:15:15', 'AUTHENTICATION_SUCCESS'),
(111, 'admin', '2019-04-23 10:18:21', 'AUTHENTICATION_SUCCESS'),
(112, 'admin', '2019-04-23 10:19:01', 'AUTHENTICATION_SUCCESS'),
(113, 'admin', '2019-04-23 10:40:05', 'AUTHENTICATION_SUCCESS'),
(114, 'admin', '2019-04-23 11:01:06', 'AUTHENTICATION_SUCCESS'),
(115, 'admin', '2019-04-23 11:06:34', 'AUTHENTICATION_SUCCESS'),
(116, 'admin', '2019-04-23 11:09:28', 'AUTHENTICATION_SUCCESS'),
(117, 'admin', '2019-04-23 11:11:32', 'AUTHENTICATION_SUCCESS'),
(118, 'admin', '2019-04-23 11:12:32', 'AUTHENTICATION_SUCCESS'),
(119, 'admin', '2019-04-23 11:20:03', 'AUTHENTICATION_SUCCESS'),
(120, 'admin', '2019-04-23 11:22:33', 'AUTHENTICATION_SUCCESS'),
(121, 'admin', '2019-04-23 11:28:03', 'AUTHENTICATION_SUCCESS'),
(122, 'admin', '2019-04-23 11:48:45', 'AUTHENTICATION_SUCCESS'),
(123, 'admin', '2019-04-23 11:59:55', 'AUTHENTICATION_SUCCESS'),
(124, 'admin', '2019-04-23 12:02:36', 'AUTHENTICATION_FAILURE'),
(125, 'admin', '2019-04-23 12:02:39', 'AUTHENTICATION_FAILURE'),
(126, 'admin', '2019-04-23 12:02:39', 'AUTHENTICATION_FAILURE'),
(127, 'admin', '2019-04-23 12:02:40', 'AUTHENTICATION_FAILURE'),
(128, 'admin', '2019-04-23 12:02:40', 'AUTHENTICATION_FAILURE'),
(129, 'admin', '2019-04-23 12:02:47', 'AUTHENTICATION_FAILURE'),
(130, 'admin', '2019-04-23 12:02:47', 'AUTHENTICATION_FAILURE'),
(131, 'admin', '2019-04-23 12:03:04', 'AUTHENTICATION_FAILURE'),
(132, 'admin', '2019-04-23 12:03:08', 'AUTHENTICATION_FAILURE'),
(133, 'admin', '2019-04-23 12:03:08', 'AUTHENTICATION_FAILURE'),
(134, 'admin', '2019-04-23 12:03:28', 'AUTHENTICATION_FAILURE'),
(135, 'admin', '2019-04-23 12:03:28', 'AUTHENTICATION_FAILURE'),
(136, 'admin', '2019-04-23 12:05:22', 'AUTHENTICATION_FAILURE'),
(137, 'admin', '2019-04-23 12:05:22', 'AUTHENTICATION_FAILURE'),
(138, 'admin', '2019-04-23 12:05:22', 'AUTHENTICATION_FAILURE'),
(139, 'admin', '2019-04-23 12:07:46', 'AUTHENTICATION_FAILURE'),
(140, 'admin', '2019-04-23 12:08:00', 'AUTHENTICATION_FAILURE'),
(141, 'admin', '2019-04-23 12:08:01', 'AUTHENTICATION_FAILURE'),
(142, 'admin', '2019-04-23 12:08:01', 'AUTHENTICATION_FAILURE'),
(143, 'admin', '2019-04-23 12:08:21', 'AUTHENTICATION_FAILURE'),
(144, 'admin', '2019-04-23 12:08:21', 'AUTHENTICATION_FAILURE'),
(145, 'admin', '2019-04-23 12:08:22', 'AUTHENTICATION_FAILURE'),
(146, 'admin', '2019-04-23 12:09:37', 'AUTHENTICATION_FAILURE'),
(147, 'admin', '2019-04-23 12:09:37', 'AUTHENTICATION_FAILURE'),
(148, 'admin', '2019-04-23 12:10:18', 'AUTHENTICATION_FAILURE'),
(149, 'admin', '2019-04-23 12:10:19', 'AUTHENTICATION_FAILURE'),
(150, 'admin', '2019-04-23 12:10:20', 'AUTHENTICATION_FAILURE'),
(151, 'admin', '2019-04-23 12:10:20', 'AUTHENTICATION_FAILURE'),
(152, 'admin', '2019-04-23 12:10:40', 'AUTHENTICATION_FAILURE'),
(153, 'admin', '2019-04-23 12:10:41', 'AUTHENTICATION_FAILURE'),
(154, 'admin', '2019-04-23 12:10:41', 'AUTHENTICATION_FAILURE'),
(155, 'admin', '2019-04-23 12:10:46', 'AUTHENTICATION_FAILURE'),
(156, 'admin', '2019-04-23 12:10:48', 'AUTHENTICATION_FAILURE'),
(157, 'admin', '2019-04-23 12:10:48', 'AUTHENTICATION_FAILURE'),
(158, 'admin', '2019-04-23 12:11:07', 'AUTHENTICATION_FAILURE'),
(159, 'admin', '2019-04-23 12:15:52', 'AUTHENTICATION_SUCCESS'),
(160, 'admin', '2019-04-23 12:23:31', 'AUTHENTICATION_FAILURE'),
(161, 'admin', '2019-04-23 12:23:31', 'AUTHENTICATION_FAILURE'),
(162, 'admin', '2019-04-23 12:23:33', 'AUTHENTICATION_FAILURE'),
(163, 'admin', '2019-04-23 12:23:33', 'AUTHENTICATION_FAILURE'),
(164, 'admin', '2019-04-23 12:34:04', 'AUTHENTICATION_FAILURE'),
(165, 'admin', '2019-04-23 12:34:04', 'AUTHENTICATION_FAILURE'),
(166, 'admin', '2019-04-23 12:34:05', 'AUTHENTICATION_FAILURE'),
(167, 'admin', '2019-04-23 12:34:05', 'AUTHENTICATION_FAILURE'),
(168, 'admin', '2019-04-23 12:34:05', 'AUTHENTICATION_FAILURE'),
(169, 'admin', '2019-04-23 12:34:05', 'AUTHENTICATION_FAILURE'),
(170, 'admin', '2019-04-23 12:35:44', 'AUTHENTICATION_FAILURE'),
(171, 'admin', '2019-04-23 12:35:45', 'AUTHENTICATION_FAILURE'),
(172, 'admin', '2019-04-23 12:35:46', 'AUTHENTICATION_FAILURE'),
(173, 'admin', '2019-04-23 12:46:57', 'AUTHENTICATION_SUCCESS'),
(174, 'admin', '2019-04-23 12:52:50', 'AUTHENTICATION_SUCCESS'),
(175, 'admin', '2019-04-23 13:06:36', 'AUTHENTICATION_SUCCESS'),
(176, 'admin', '2019-04-23 13:14:22', 'AUTHENTICATION_SUCCESS'),
(177, 'admin', '2019-04-23 14:52:27', 'AUTHENTICATION_SUCCESS'),
(178, 'admin', '2019-04-23 15:05:02', 'AUTHENTICATION_SUCCESS'),
(179, 'admin', '2019-04-23 15:10:18', 'AUTHENTICATION_SUCCESS'),
(180, 'admin', '2019-04-23 15:10:18', 'AUTHENTICATION_SUCCESS'),
(181, 'admin', '2019-04-23 15:10:18', 'AUTHENTICATION_SUCCESS'),
(182, 'admin', '2019-04-23 15:10:18', 'AUTHENTICATION_SUCCESS'),
(183, 'admin', '2019-04-23 15:10:18', 'AUTHENTICATION_SUCCESS'),
(184, 'admin', '2019-04-23 15:10:18', 'AUTHENTICATION_SUCCESS'),
(185, 'admin', '2019-04-23 15:16:09', 'AUTHENTICATION_SUCCESS'),
(186, 'admin', '2019-04-23 15:18:02', 'AUTHENTICATION_SUCCESS'),
(187, 'admin', '2019-04-23 15:19:04', 'AUTHENTICATION_SUCCESS'),
(188, 'admin', '2019-04-23 15:23:09', 'AUTHENTICATION_SUCCESS'),
(189, 'admin', '2019-04-23 15:25:54', 'AUTHENTICATION_SUCCESS'),
(190, 'admin', '2019-04-23 15:29:20', 'AUTHENTICATION_SUCCESS'),
(191, 'admin', '2019-04-23 15:30:02', 'AUTHENTICATION_SUCCESS'),
(192, 'admin', '2019-04-23 15:31:57', 'AUTHENTICATION_SUCCESS'),
(193, 'admin', '2019-04-23 15:36:01', 'AUTHENTICATION_SUCCESS'),
(194, 'admin', '2019-04-23 18:05:23', 'AUTHENTICATION_SUCCESS'),
(195, 'admin', '2019-04-23 18:07:10', 'AUTHENTICATION_SUCCESS'),
(196, 'admin', '2019-04-24 13:26:57', 'AUTHENTICATION_SUCCESS'),
(197, 'admin', '2019-04-24 13:36:53', 'AUTHENTICATION_SUCCESS'),
(198, 'admin', '2019-04-24 13:38:44', 'AUTHENTICATION_SUCCESS'),
(199, 'admin', '2019-04-24 13:43:41', 'AUTHENTICATION_SUCCESS'),
(200, 'admin', '2019-04-24 13:46:09', 'AUTHENTICATION_SUCCESS'),
(201, 'admin', '2019-04-24 13:51:06', 'AUTHENTICATION_SUCCESS'),
(202, 'admin', '2019-04-24 14:08:45', 'AUTHENTICATION_SUCCESS'),
(203, 'admin', '2019-04-24 14:08:59', 'AUTHENTICATION_SUCCESS'),
(204, 'admin', '2019-04-24 14:10:22', 'AUTHENTICATION_SUCCESS'),
(205, 'admin', '2019-04-25 09:59:12', 'AUTHENTICATION_SUCCESS'),
(206, 'admin', '2019-04-25 10:03:51', 'AUTHENTICATION_SUCCESS'),
(207, 'admin', '2019-04-25 10:04:06', 'AUTHENTICATION_SUCCESS'),
(208, 'admin', '2019-04-25 10:04:53', 'AUTHENTICATION_SUCCESS'),
(209, 'admin', '2019-04-25 10:05:22', 'AUTHENTICATION_SUCCESS');

-- --------------------------------------------------------

--
-- Structure de la table `jhi_persistent_audit_evt_data`
--

CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `jhi_persistent_audit_evt_data`
--

INSERT INTO `jhi_persistent_audit_evt_data` (`event_id`, `name`, `value`) VALUES
(124, 'message', 'Bad credentials'),
(124, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(125, 'message', 'Bad credentials'),
(125, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(126, 'message', 'Bad credentials'),
(126, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(127, 'message', 'Bad credentials'),
(127, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(128, 'message', 'Bad credentials'),
(128, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(129, 'message', 'Bad credentials'),
(129, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(130, 'message', 'Bad credentials'),
(130, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(131, 'message', 'Bad credentials'),
(131, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(132, 'message', 'Bad credentials'),
(132, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(133, 'message', 'Bad credentials'),
(133, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(134, 'message', 'Bad credentials'),
(134, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(135, 'message', 'Bad credentials'),
(135, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(136, 'message', 'Bad credentials'),
(136, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(137, 'message', 'Bad credentials'),
(137, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(138, 'message', 'Bad credentials'),
(138, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(139, 'message', 'Bad credentials'),
(139, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(140, 'message', 'Bad credentials'),
(140, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(141, 'message', 'Bad credentials'),
(141, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(142, 'message', 'Bad credentials'),
(142, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(143, 'message', 'Bad credentials'),
(143, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(144, 'message', 'Bad credentials'),
(144, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(145, 'message', 'Bad credentials'),
(145, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(146, 'message', 'Bad credentials'),
(146, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(147, 'message', 'Bad credentials'),
(147, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(148, 'message', 'Bad credentials'),
(148, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(149, 'message', 'Bad credentials'),
(149, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(150, 'message', 'Bad credentials'),
(150, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(151, 'message', 'Bad credentials'),
(151, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(152, 'message', 'Bad credentials'),
(152, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(153, 'message', 'Bad credentials'),
(153, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(154, 'message', 'Bad credentials'),
(154, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(155, 'message', 'Bad credentials'),
(155, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(156, 'message', 'Bad credentials'),
(156, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(157, 'message', 'Bad credentials'),
(157, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(158, 'message', 'Bad credentials'),
(158, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(160, 'message', 'Bad credentials'),
(160, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(161, 'message', 'Bad credentials'),
(161, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(162, 'message', 'Bad credentials'),
(162, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(163, 'message', 'Bad credentials'),
(163, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(164, 'message', 'Bad credentials'),
(164, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(165, 'message', 'Bad credentials'),
(165, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(166, 'message', 'Bad credentials'),
(166, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(167, 'message', 'Bad credentials'),
(167, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(168, 'message', 'Bad credentials'),
(168, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(169, 'message', 'Bad credentials'),
(169, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(170, 'message', 'Bad credentials'),
(170, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(171, 'message', 'Bad credentials'),
(171, 'type', 'org.springframework.security.authentication.BadCredentialsException'),
(172, 'message', 'Bad credentials'),
(172, 'type', 'org.springframework.security.authentication.BadCredentialsException');

-- --------------------------------------------------------

--
-- Structure de la table `jhi_user`
--

CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(191) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(6) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `jhi_user`
--

INSERT INTO `jhi_user` (`id`, `login`, `password_hash`, `first_name`, `last_name`, `email`, `image_url`, `activated`, `lang_key`, `activation_key`, `reset_key`, `created_by`, `created_date`, `reset_date`, `last_modified_by`, `last_modified_date`) VALUES
(1, 'system', '$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG', 'System', 'System', 'system@localhost', '', b'1', 'fr', NULL, NULL, 'system', NULL, NULL, 'system', NULL),
(2, 'anonymoususer', '$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO', 'Anonymous', 'User', 'anonymous@localhost', '', b'1', 'fr', NULL, NULL, 'system', NULL, NULL, 'system', NULL),
(3, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'Administrator', 'Administrator', 'admin@localhost', '', b'1', 'fr', NULL, NULL, 'system', NULL, NULL, 'system', NULL),
(4, 'user', '$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K', 'User', 'User', 'user@localhost', '', b'1', 'fr', NULL, NULL, 'system', NULL, NULL, 'system', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `jhi_user_authority`
--

CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `jhi_user_authority`
--

INSERT INTO `jhi_user_authority` (`user_id`, `authority_name`) VALUES
(1, 'ROLE_ADMIN'),
(1, 'ROLE_USER'),
(3, 'ROLE_ADMIN'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Structure de la table `metier`
--

CREATE TABLE `metier` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `etatmetier` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `moyen_transport`
--

CREATE TABLE `moyen_transport` (
  `id` bigint(20) NOT NULL,
  `matricule` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `vitesse` varchar(255) DEFAULT NULL,
  `capacite` varchar(255) DEFAULT NULL,
  `type_transport_id` bigint(20) DEFAULT NULL,
  `date_list` tinyblob
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `parfum`
--

CREATE TABLE `parfum` (
  `id` bigint(20) NOT NULL,
  `numeroparf` varchar(255) DEFAULT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `prospection`
--

CREATE TABLE `prospection` (
  `id` bigint(20) NOT NULL,
  `journee` varchar(255) DEFAULT NULL,
  `nbreatteint` varchar(255) DEFAULT NULL,
  `nbrprevue` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `moyen_transport_id` bigint(20) DEFAULT NULL,
  `commande_id` bigint(20) DEFAULT NULL,
  `gerant_id` bigint(20) DEFAULT NULL,
  `prevendeur_id` bigint(20) DEFAULT NULL,
  `datecreation` tinyblob,
  `datedepart` tinyblob,
  `heurearrive` varchar(255) DEFAULT NULL,
  `heuredepart` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `prospection`
--

INSERT INTO `prospection` (`id`, `journee`, `nbreatteint`, `nbrprevue`, `etat`, `moyen_transport_id`, `commande_id`, `gerant_id`, `prevendeur_id`, `datecreation`, `datedepart`, `heurearrive`, `heuredepart`) VALUES
(2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `qualite`
--

CREATE TABLE `qualite` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `reglement`
--

CREATE TABLE `reglement` (
  `id` bigint(20) NOT NULL,
  `date_reg` date DEFAULT NULL,
  `montant_payer` varchar(255) DEFAULT NULL,
  `type_rglment_id` bigint(20) DEFAULT NULL,
  `commande_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `route`
--

CREATE TABLE `route` (
  `id` bigint(20) NOT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `journee` varchar(255) DEFAULT NULL,
  `heure_dep_com` varchar(255) DEFAULT NULL,
  `heure_fin_com` varchar(255) DEFAULT NULL,
  `heure_dep_liv` varchar(255) DEFAULT NULL,
  `heure_fin_liv` varchar(255) DEFAULT NULL,
  `journee_liv` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `moyen_transport_id` bigint(20) DEFAULT NULL,
  `commande_id` bigint(20) DEFAULT NULL,
  `gerant_commande_id` bigint(20) DEFAULT NULL,
  `livraison_id` bigint(20) DEFAULT NULL,
  `livreur_id` bigint(20) DEFAULT NULL,
  `prevendeur_id` bigint(20) DEFAULT NULL,
  `date_creation` tinyblob,
  `date_r_com` tinyblob,
  `date_r_liv` tinyblob,
  `datedep_com` tinyblob,
  `datedep_liv` tinyblob,
  `gerantlivraison_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `secteur`
--

CREATE TABLE `secteur` (
  `id` bigint(20) NOT NULL,
  `nom_secteur` varchar(255) DEFAULT NULL,
  `longitutde` varchar(255) DEFAULT NULL,
  `altitude` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `secteur_commune`
--

CREATE TABLE `secteur_commune` (
  `commune_id` bigint(20) NOT NULL,
  `secteur_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `secteur_route`
--

CREATE TABLE `secteur_route` (
  `route_id` bigint(20) NOT NULL,
  `secteur_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `stock_initial`
--

CREATE TABLE `stock_initial` (
  `id` bigint(20) NOT NULL,
  `qte_stock_init` varchar(255) DEFAULT NULL,
  `qte_stock_encours` varchar(255) DEFAULT NULL,
  `boutique_id` bigint(20) DEFAULT NULL,
  `article_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `tablette`
--

CREATE TABLE `tablette` (
  `id` bigint(20) NOT NULL,
  `numero` varchar(255) DEFAULT NULL,
  `date_serv` date DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL,
  `numeropuce` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `type_rglment`
--

CREATE TABLE `type_rglment` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `type_route`
--

CREATE TABLE `type_route` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL,
  `etat` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `type_transport`
--

CREATE TABLE `type_transport` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `appro`
--
ALTER TABLE `appro`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_appro_article_id` (`article_id`),
  ADD KEY `fk_appro_bon_livraison_id` (`bon_livraison_id`);

--
-- Index pour la table `article`
--
ALTER TABLE `article`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_article_parfum_id` (`parfum_id`),
  ADD KEY `fk_article_depot_id` (`depot_id`);

--
-- Index pour la table `bon_livraison`
--
ALTER TABLE `bon_livraison`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `boutique`
--
ALTER TABLE `boutique`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_boutique_metier_id` (`metier_id`),
  ADD KEY `fk_boutique_qualite_id` (`qualite_id`),
  ADD KEY `fk_boutique_secteur_id` (`secteur_id`),
  ADD KEY `fk_boutique_prospection_id` (`prospection_id`);

--
-- Index pour la table `boutique_route`
--
ALTER TABLE `boutique_route`
  ADD PRIMARY KEY (`boutique_id`,`route_id`),
  ADD KEY `fk_boutique_route_route_id` (`route_id`);

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_commande_boutique_id` (`boutique_id`),
  ADD UNIQUE KEY `ux_commande_detail_route_id` (`detail_route_id`),
  ADD KEY `fk_commande_secteur_id` (`secteur_id`),
  ADD KEY `FKldtjtj41nkdukep1x6b498fsj` (`prospection_id`),
  ADD KEY `FK19h4r9hnt3pjwlrn7xk68ifmc` (`prevendeur_id`),
  ADD KEY `FKplbyqofyoknimfxc0hqiecj5n` (`livreur_id`);

--
-- Index pour la table `commune`
--
ALTER TABLE `commune`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `databasechangeloglock`
--
ALTER TABLE `databasechangeloglock`
  ADD PRIMARY KEY (`ID`);

--
-- Index pour la table `depot`
--
ALTER TABLE `depot`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `detail_com`
--
ALTER TABLE `detail_com`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_detail_com_article_id` (`article_id`),
  ADD KEY `fk_detail_com_commande_id` (`commande_id`);

--
-- Index pour la table `detail_route`
--
ALTER TABLE `detail_route`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_detail_route_route_id` (`route_id`),
  ADD KEY `fk_detail_route_boutique_id` (`boutique_id`);

--
-- Index pour la table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_employee_user_id` (`user_id`),
  ADD UNIQUE KEY `UC_EMPLOYEETABLETTE_ID_COL` (`tablette_id`);

--
-- Index pour la table `jhi_authority`
--
ALTER TABLE `jhi_authority`
  ADD PRIMARY KEY (`name`);

--
-- Index pour la table `jhi_persistent_audit_event`
--
ALTER TABLE `jhi_persistent_audit_event`
  ADD PRIMARY KEY (`event_id`),
  ADD KEY `idx_persistent_audit_event` (`principal`,`event_date`);

--
-- Index pour la table `jhi_persistent_audit_evt_data`
--
ALTER TABLE `jhi_persistent_audit_evt_data`
  ADD PRIMARY KEY (`event_id`,`name`),
  ADD KEY `idx_persistent_audit_evt_data` (`event_id`);

--
-- Index pour la table `jhi_user`
--
ALTER TABLE `jhi_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ux_user_login` (`login`),
  ADD UNIQUE KEY `ux_user_email` (`email`);

--
-- Index pour la table `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD PRIMARY KEY (`user_id`,`authority_name`),
  ADD KEY `fk_authority_name` (`authority_name`);

--
-- Index pour la table `metier`
--
ALTER TABLE `metier`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `moyen_transport`
--
ALTER TABLE `moyen_transport`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_moyen_transport_type_transport_id` (`type_transport_id`);

--
-- Index pour la table `parfum`
--
ALTER TABLE `parfum`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `prospection`
--
ALTER TABLE `prospection`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_prospection_moyen_transport_id` (`moyen_transport_id`),
  ADD KEY `fk_prospection_commande_id` (`commande_id`),
  ADD KEY `FK99b577cidr56lywx6h3mo4q70` (`prevendeur_id`),
  ADD KEY `FKsf5lyx65cpv7urxqw0gvi8m6s` (`gerant_id`);

--
-- Index pour la table `qualite`
--
ALTER TABLE `qualite`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `reglement`
--
ALTER TABLE `reglement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_reglement_type_rglment_id` (`type_rglment_id`),
  ADD KEY `fk_reglement_commande_id` (`commande_id`);

--
-- Index pour la table `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_route_moyen_transport_id` (`moyen_transport_id`),
  ADD KEY `FK8lp2yq20ybq8xm1whsgk0lg7k` (`commande_id`),
  ADD KEY `FKkvbpthqr7hxsx2qe637onekyv` (`gerant_commande_id`),
  ADD KEY `FKq9leed88at43a7nlaacjql164` (`livraison_id`),
  ADD KEY `FKhsvugeqkd24oeujj9wc4a55gd` (`livreur_id`),
  ADD KEY `FKhtob5dfg8haa51r9w0qr0q0ei` (`prevendeur_id`),
  ADD KEY `FKon5ebhxppvuod2p0709ompqaa` (`gerantlivraison_id`);

--
-- Index pour la table `secteur`
--
ALTER TABLE `secteur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `secteur_commune`
--
ALTER TABLE `secteur_commune`
  ADD PRIMARY KEY (`secteur_id`,`commune_id`),
  ADD KEY `fk_secteur_commune_commune_id` (`commune_id`);

--
-- Index pour la table `secteur_route`
--
ALTER TABLE `secteur_route`
  ADD PRIMARY KEY (`secteur_id`,`route_id`),
  ADD KEY `fk_secteur_route_route_id` (`route_id`);

--
-- Index pour la table `stock_initial`
--
ALTER TABLE `stock_initial`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_stock_initial_boutique_id` (`boutique_id`),
  ADD KEY `fk_stock_initial_article_id` (`article_id`);

--
-- Index pour la table `tablette`
--
ALTER TABLE `tablette`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_rglment`
--
ALTER TABLE `type_rglment`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_route`
--
ALTER TABLE `type_route`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_transport`
--
ALTER TABLE `type_transport`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `appro`
--
ALTER TABLE `appro`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `article`
--
ALTER TABLE `article`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `bon_livraison`
--
ALTER TABLE `bon_livraison`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `boutique`
--
ALTER TABLE `boutique`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `commande`
--
ALTER TABLE `commande`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `commune`
--
ALTER TABLE `commune`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `depot`
--
ALTER TABLE `depot`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `detail_com`
--
ALTER TABLE `detail_com`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `detail_route`
--
ALTER TABLE `detail_route`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `jhi_persistent_audit_event`
--
ALTER TABLE `jhi_persistent_audit_event`
  MODIFY `event_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=210;

--
-- AUTO_INCREMENT pour la table `jhi_user`
--
ALTER TABLE `jhi_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `metier`
--
ALTER TABLE `metier`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `moyen_transport`
--
ALTER TABLE `moyen_transport`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `parfum`
--
ALTER TABLE `parfum`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `prospection`
--
ALTER TABLE `prospection`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `qualite`
--
ALTER TABLE `qualite`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `reglement`
--
ALTER TABLE `reglement`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `route`
--
ALTER TABLE `route`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `secteur`
--
ALTER TABLE `secteur`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `stock_initial`
--
ALTER TABLE `stock_initial`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `tablette`
--
ALTER TABLE `tablette`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `type_rglment`
--
ALTER TABLE `type_rglment`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `type_route`
--
ALTER TABLE `type_route`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `type_transport`
--
ALTER TABLE `type_transport`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `appro`
--
ALTER TABLE `appro`
  ADD CONSTRAINT `fk_appro_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  ADD CONSTRAINT `fk_appro_bon_livraison_id` FOREIGN KEY (`bon_livraison_id`) REFERENCES `bon_livraison` (`id`);

--
-- Contraintes pour la table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `fk_article_depot_id` FOREIGN KEY (`depot_id`) REFERENCES `depot` (`id`),
  ADD CONSTRAINT `fk_article_parfum_id` FOREIGN KEY (`parfum_id`) REFERENCES `parfum` (`id`);

--
-- Contraintes pour la table `boutique`
--
ALTER TABLE `boutique`
  ADD CONSTRAINT `fk_boutique_metier_id` FOREIGN KEY (`metier_id`) REFERENCES `metier` (`id`),
  ADD CONSTRAINT `fk_boutique_prospection_id` FOREIGN KEY (`prospection_id`) REFERENCES `prospection` (`id`),
  ADD CONSTRAINT `fk_boutique_qualite_id` FOREIGN KEY (`qualite_id`) REFERENCES `qualite` (`id`),
  ADD CONSTRAINT `fk_boutique_secteur_id` FOREIGN KEY (`secteur_id`) REFERENCES `secteur` (`id`);

--
-- Contraintes pour la table `boutique_route`
--
ALTER TABLE `boutique_route`
  ADD CONSTRAINT `fk_boutique_route_boutique_id` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`),
  ADD CONSTRAINT `fk_boutique_route_route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`);

--
-- Contraintes pour la table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `FK19h4r9hnt3pjwlrn7xk68ifmc` FOREIGN KEY (`prevendeur_id`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `FKldtjtj41nkdukep1x6b498fsj` FOREIGN KEY (`prospection_id`) REFERENCES `prospection` (`id`),
  ADD CONSTRAINT `FKplbyqofyoknimfxc0hqiecj5n` FOREIGN KEY (`livreur_id`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `fk_commande_boutique_id` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`),
  ADD CONSTRAINT `fk_commande_detail_route_id` FOREIGN KEY (`detail_route_id`) REFERENCES `detail_route` (`id`),
  ADD CONSTRAINT `fk_commande_secteur_id` FOREIGN KEY (`secteur_id`) REFERENCES `secteur` (`id`);

--
-- Contraintes pour la table `detail_com`
--
ALTER TABLE `detail_com`
  ADD CONSTRAINT `fk_detail_com_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  ADD CONSTRAINT `fk_detail_com_commande_id` FOREIGN KEY (`commande_id`) REFERENCES `commande` (`id`);

--
-- Contraintes pour la table `detail_route`
--
ALTER TABLE `detail_route`
  ADD CONSTRAINT `fk_detail_route_boutique_id` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`),
  ADD CONSTRAINT `fk_detail_route_route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`);

--
-- Contraintes pour la table `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `FK2uuwg9bodewqilxjxfu6mfxn` FOREIGN KEY (`tablette_id`) REFERENCES `tablette` (`id`),
  ADD CONSTRAINT `fk_employee_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Contraintes pour la table `jhi_persistent_audit_evt_data`
--
ALTER TABLE `jhi_persistent_audit_evt_data`
  ADD CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`);

--
-- Contraintes pour la table `jhi_user_authority`
--
ALTER TABLE `jhi_user_authority`
  ADD CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  ADD CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`);

--
-- Contraintes pour la table `moyen_transport`
--
ALTER TABLE `moyen_transport`
  ADD CONSTRAINT `fk_moyen_transport_type_transport_id` FOREIGN KEY (`type_transport_id`) REFERENCES `type_transport` (`id`);

--
-- Contraintes pour la table `prospection`
--
ALTER TABLE `prospection`
  ADD CONSTRAINT `FK99b577cidr56lywx6h3mo4q70` FOREIGN KEY (`prevendeur_id`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `FKsf5lyx65cpv7urxqw0gvi8m6s` FOREIGN KEY (`gerant_id`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `fk_prospection_commande_id` FOREIGN KEY (`commande_id`) REFERENCES `commande` (`id`),
  ADD CONSTRAINT `fk_prospection_moyen_transport_id` FOREIGN KEY (`moyen_transport_id`) REFERENCES `moyen_transport` (`id`);

--
-- Contraintes pour la table `reglement`
--
ALTER TABLE `reglement`
  ADD CONSTRAINT `fk_reglement_commande_id` FOREIGN KEY (`commande_id`) REFERENCES `commande` (`id`),
  ADD CONSTRAINT `fk_reglement_type_rglment_id` FOREIGN KEY (`type_rglment_id`) REFERENCES `type_rglment` (`id`);

--
-- Contraintes pour la table `route`
--
ALTER TABLE `route`
  ADD CONSTRAINT `FK8lp2yq20ybq8xm1whsgk0lg7k` FOREIGN KEY (`commande_id`) REFERENCES `type_route` (`id`),
  ADD CONSTRAINT `FKhsvugeqkd24oeujj9wc4a55gd` FOREIGN KEY (`livreur_id`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `FKhtob5dfg8haa51r9w0qr0q0ei` FOREIGN KEY (`prevendeur_id`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `FKkvbpthqr7hxsx2qe637onekyv` FOREIGN KEY (`gerant_commande_id`) REFERENCES `jhi_user` (`id`),
  ADD CONSTRAINT `FKon5ebhxppvuod2p0709ompqaa` FOREIGN KEY (`gerantlivraison_id`) REFERENCES `employee` (`id`),
  ADD CONSTRAINT `FKq9leed88at43a7nlaacjql164` FOREIGN KEY (`livraison_id`) REFERENCES `type_route` (`id`),
  ADD CONSTRAINT `fk_route_moyen_transport_id` FOREIGN KEY (`moyen_transport_id`) REFERENCES `moyen_transport` (`id`);

--
-- Contraintes pour la table `secteur_commune`
--
ALTER TABLE `secteur_commune`
  ADD CONSTRAINT `fk_secteur_commune_commune_id` FOREIGN KEY (`commune_id`) REFERENCES `commune` (`id`),
  ADD CONSTRAINT `fk_secteur_commune_secteur_id` FOREIGN KEY (`secteur_id`) REFERENCES `secteur` (`id`);

--
-- Contraintes pour la table `secteur_route`
--
ALTER TABLE `secteur_route`
  ADD CONSTRAINT `fk_secteur_route_route_id` FOREIGN KEY (`route_id`) REFERENCES `route` (`id`),
  ADD CONSTRAINT `fk_secteur_route_secteur_id` FOREIGN KEY (`secteur_id`) REFERENCES `secteur` (`id`);

--
-- Contraintes pour la table `stock_initial`
--
ALTER TABLE `stock_initial`
  ADD CONSTRAINT `fk_stock_initial_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  ADD CONSTRAINT `fk_stock_initial_boutique_id` FOREIGN KEY (`boutique_id`) REFERENCES `boutique` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
