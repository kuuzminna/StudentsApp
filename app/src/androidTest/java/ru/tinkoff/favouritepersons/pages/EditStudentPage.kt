package ru.tinkoff.favouritepersons.pages

import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import io.qameta.allure.kotlin.Allure.step
import ru.tinkoff.favouritepersons.R

class EditStudentPage : BasePage() {
    private val nameEdit = KEditText { withId(R.id.et_name) }
    private val surnameEdit = KEditText { withId(R.id.et_surname) }
    private val genderEdit = KEditText { withId(R.id.et_gender) }
    private val birthdayEdit = KEditText { withId(R.id.et_birthdate) }
    private val emailEdit = KEditText { withId(R.id.et_email) }
    private val phoneEdit = KEditText { withId(R.id.et_phone) }
    private val addressEdit = KEditText { withId(R.id.et_address) }
    private val imageEdit = KEditText { withId(R.id.et_image) }
    private val scoreEdit = KEditText { withId(R.id.et_score) }
    private val submitButton = KButton { withId(R.id.submit_button) }

    private val genderError = KTextView { withText("Поле должно быть заполнено буквами М или Ж") }

    fun typeTextInName(name: String) {
        step("Вводим текст \"$name\" в поле Имени") {
            nameEdit.typeText(name)
        }
    }

    fun replaceTextInName(name: String) {
        step("Заменяем текст \"$name\" в поле Имени") {
            nameEdit.replaceText(name)
        }
    }

    fun typeTextInSurname(surname: String) {
        step("Вводим текст \"$surname\" в поле Фамилия") {
            surnameEdit.typeText(surname)
        }
    }

    fun replaceTextInGender(gender: String) {
        step("Вводим текст \"$gender\" в поле Пол") {
            genderEdit.replaceText(gender)
        }
    }

    fun typeTextInBirthday(birthday: String) {
        step("Вводим текст \"$birthday\" в поле Дата рождения") {
            birthdayEdit.typeText(birthday)
        }
    }

    fun typeTextInEmail(email: String) {
        step("Вводим текст \"$email\" в поле email") {
            emailEdit.typeText(email)
        }
    }

    fun typeTextInPhone(phone: String) {
        step("Вводим текст \"$phone\" в поле Телефон") {
            phoneEdit.typeText(phone)
        }
    }

    fun typeTextInAddress(address: String) {
        step("Вводим текст \"$address\" в поле Адрес") {
            addressEdit.typeText(address)
        }
    }

    fun typeTextInImageLink(imageLink: String) {
        step("Вводим текст \"$imageLink\" в поле Изображение") {
            imageEdit.typeText(imageLink)
        }
    }

    fun typeTextInScore(score: String) {
        step("Вводим текст \"$score\" в поле Рейтинг") {
            scoreEdit.typeText(score)
        }
    }

    fun clearTextInGender() {
        step("Очистить значение в поле Пол") {
            genderEdit.clearText()
        }
    }

    fun checkNameHasText(name: String) {
        step("Проверить, что в поле Имя значение \"$name\"") {
            nameEdit.hasText(name)
        }
    }

    fun checkSurnameHasText(surname: String) {
        step("Проверить, что в поле Фамилия значение \"$surname\"") {
            surnameEdit.hasText(surname)
        }
    }

    fun checkGenderHasText(gender: String) {
        step("Проверить, что в поле Пол значение \"$gender\"") {
            genderEdit.hasText(gender)
        }
    }

    fun checkBirthdayHasText(birthday: String) {
        step("Проверить, что в поле Дата рождения значение \"$birthday\"") {
            birthdayEdit.hasText(birthday)
        }
    }

    fun checkGenderErrorIsDisplayed() {
        step("Проверить, что хинт об ошибке в поле Пол отображается") {
            genderError.isDisplayed()
        }
    }

    fun checkGenderErrorIsNotDisplayed() {
        step("Проверить, что хинт об ошибке в поле Пол не отображается") {
            genderError.doesNotExist()
        }
    }

    fun clickSubmitButton() {
        step("Тап на кнопку \"Сохранить\"") {
            submitButton.click()
        }
    }

    companion object {
        inline operator fun invoke(crossinline block: EditStudentPage.() -> Unit) =
            step("Страница редактирование человека") {
                EditStudentPage().block()
            }
    }

}