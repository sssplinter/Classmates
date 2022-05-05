package com.breaktime.classmates.domain.entities.data

object CurrentUser {
    var userId: String = ""
        private set
    var token: String = ""
        private set
    var name: String = ""
        private set
    var surname: String = ""
        private set
    var email: String = ""
        private set
    var bio: String = ""
        private set
    var profileImageUrl: String = ""
        private set
    var universities: List<Any> = emptyList()
        private set

    fun setData(
        userId: String = this.userId,
        token: String = this.token,
        name: String = this.name,
        surname: String = this.surname,
        email: String = this.email,
        bio: String = this.bio,
        profileImageUrl: String = this.profileImageUrl,
        universities: List<Any> = this.universities,
    ) {
        this.userId = userId
        this.token = token
        this.name = name
        this.surname = surname
        this.email = email
        this.bio = bio
        this.profileImageUrl = profileImageUrl
        this.universities = universities
    }
}