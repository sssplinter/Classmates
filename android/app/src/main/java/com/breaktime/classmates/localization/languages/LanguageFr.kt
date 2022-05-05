package com.breaktime.classmates.localization.languages

import com.breaktime.classmates.localization.Language

object LanguageFr : Language {
    override val appName: String
        get() = "Camarades"
    override val login: String
        get() = "Login"
    override val signIn: String
        get() = "Se connecter"
    override val signUp: String
        get() = "Creer nouveau compte"
    override val email: String
        get() = "email"
    override val password: String
        get() = "le mot de passe"
    override val repeatPassword: String
        get() = "répéter le mot de passe"
    override val emptyEmailError: String
        get() = "l'e-mail est vide"
    override val wrongEmailError: String
        get() = "adresse Email incorrecte"
    override val emptyPasswordError: String
        get() = "le mot de passe est vide"
    override val wrongPasswordLengthError: String
        get() = "le mot de passe doit comporter plus de 8 signes"
    override val differentPasswordError: String
        get() = "les mots de passe sont différents"
    override val settings: String
        get() = "Réglages"
    override val searchMsg: String
        get() = "Trouver un message..."
    override val msg: String
        get() = "Message..."
    override val yourConnections: String
        get() = "Vos connexions"
    override val search: String
        get() = "Recherche"
    override val group: String
        get() = "Groupe: "
    override val logOut: String
        get() = "Se déconnecter"
    override val editGroup: String
        get() = "editer ->"
    override val setupUserInfo: String
        get() = "Configurer les informations utilisateur"
    override val name: String
        get() = "Nom"
    override val nameNotEmpty: String
        get() = "Le nom ne doit pas être vide"
    override val surname: String
        get() = "Prenom"
    override val surnameNotEmpty: String
        get() = "Le prenom ne doit pas être vide"
    override val confirm: String
        get() = "Confirmer"
    override val friends: String
        get() = "Amis"
    override val subscribers: String
        get() = "Les abonnés"
    override val noInternet: String
        get() = "Pas de connexion Internet"
    override val noInternetMsg: String
        get() = "Veuillez vous reconnecter au réseau"
    override val subscriptions: String
        get() = "Abonnements"
    override val findAllPeople: String
        get() = "Trouver toutes les personnes"
    override val actionImgContent: String
        get() = "Action image"
    override val errorImgContent: String
        get() = "Error image"
    override val logoImgContent: String
        get() = "Logo icon"
    override val emailImgContent: String
        get() = "Email icon"
    override val bio: String
        get() = "BIO"
    override val anyDetails: String
        get() = "Tous les détails tels que l'âge, la profession ou la ville"
    override val enterYourName: String
        get() = "Entrez votre nom et ajoutez une photo de profil"
    override val theme: String
        get() = "Thème"
    override val language: String
        get() = "Langue"
    override val sendMsg: String
        get() = "Envoyer le message"
    override val privateMsgSend: String
        get() = "Ce message sera envoyé aux messages privés"
    override val message: String
        get() = "message"
    override val cancel: String
        get() = "Annuler"
    override val send: String
        get() = "Envoyé"
}