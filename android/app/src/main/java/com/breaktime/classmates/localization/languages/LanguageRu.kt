package com.breaktime.classmates.localization.languages

import com.breaktime.classmates.localization.Language

object LanguageRu : Language {
    override val appName: String
        get() = "Одногруппники"
    override val login: String
        get() = "Логин"
    override val signIn: String
        get() = "Войти"
    override val signUp: String
        get() = "Зарегистироваться"
    override val email: String
        get() = "email"
    override val password: String
        get() = "Пароль"
    override val repeatPassword: String
        get() = "Подтверждение пароля"
    override val emptyEmailError: String
        get() = "поле email не должно быть пустым"
    override val wrongEmailError: String
        get() = "Неверный email"
    override val emptyPasswordError: String
        get() = "поле пароля не должно быть пустым"
    override val wrongPasswordLengthError: String
        get() = "пароль должен содержать не менее 8 символов"
    override val differentPasswordError: String
        get() = "пароли не совпадают"
    override val settings: String
        get() = "Настройки"
    override val searchMsg: String
        get() = "Поиск сообщения..."
    override val msg: String
        get() = "Сообщение..."
    override val yourConnections: String
        get() = "Ваши контакты"
    override val search: String
        get() = "Поиск"
    override val friends: String
        get() = "Друзья"
    override val subscribers: String
        get() = "Подписчики"
    override val noInternet: String
        get() = "Отсутствует интернет соединение"
    override val noInternetMsg: String
        get() = "Пожалуйста, проверьте подключение"
    override val subscriptions: String
        get() = "Подписки"
    override val findAllPeople: String
        get() = "Найти всех людей"
    override val actionImgContent: String
        get() = "Картинка действия"
    override val errorImgContent: String
        get() = "Ошибка картинки"
    override val logoImgContent: String
        get() = "Лого иконка"
    override val emailImgContent: String
        get() = "Email иконка"
    override val logOut: String
        get() = "Выйти"
    override val group: String
        get() = "Группа"
    override val editGroup: String
        get() = "изменить ->"
    override val setupUserInfo: String
        get() = "Настроить информацию о пользователе"
    override val name: String
        get() = "Имя"
    override val nameNotEmpty: String
        get() = "Поле имени должно быть заполнено"
    override val surname: String
        get() = "Фаимлия"
    override val surnameNotEmpty: String
        get() = "Поле имени должно быть заполнено"
    override val confirm: String
        get() = "Подтвердить"
    override val bio: String
        get() = "BIO"
    override val anyDetails: String
        get() = "Введите информацию о себе, которую посчитате нужной"
    override val enterYourName: String
        get() = "Введите свое имя и выбирите фото профиля"
    override val theme: String
        get() = "Тема"
    override val language: String
        get() = "Язык"
    override val sendMsg: String
        get() = "Отправить сообщение"
    override val privateMsgSend: String
        get() = "Это сообщение будет оправленно  в приватный диалог"
    override val message: String
        get() = "Сообщение"
    override val cancel: String
        get() = "Закрыть"
    override val send: String
        get() = "Отправить"
}