package ru.tinkoff.favouritepersons.tests

import io.qameta.allure.kotlin.AllureId
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.Test
import ru.tinkoff.favouritepersons.PersonDescription
import ru.tinkoff.favouritepersons.pages.EditStudentPage
import ru.tinkoff.favouritepersons.pages.MainPage

class EditStudentPageTests : BaseTest() {

    @Test
    @AllureId("5")
    @DisplayName("Проверка открытия второго экрана с данными пользователя")
    fun checkOpeningSecondScreenWithUserData() = run {
        mock(listOf(PersonDescription()))
        MainPage {
            clickButtonAddPerson()
            clickButtonNetwork()
            clickPersonInPosition(0)
        }
        EditStudentPage {
            checkNameHasText("Leo")
            checkSurnameHasText("Lampo")
            checkGenderHasText("М")
            checkBirthdayHasText("1951-08-01")
        }
    }

    @Test
    @AllureId("6")
    @DisplayName("Проверка редактирования студента")
    fun checkStudentsEditing() = run {
        mock(listOf(PersonDescription()))
        MainPage {
            clickButtonAddPerson()
            clickButtonNetwork()
            clickPersonInPosition(0)
        }
        EditStudentPage {
            replaceTextInName("Иосиф")
            clickSubmitButton()
        }
        MainPage {
            checkNameInPosition(0, "Иосиф Lampo")
        }
    }

    @Test
    @AllureId("7")
    @DisplayName("Проверка добавления студента")
    fun checkAdditionStudent() = run {
        MainPage {
            clickButtonAddPerson()
            clickButtonManually()
        }
        EditStudentPage {
            typeTextInName(enteredName)
            typeTextInSurname(enteredSurname)
            replaceTextInGender(enteredGender)
            typeTextInBirthday(enteredBirthday)
            typeTextInEmail(enteredEmail)
            typeTextInPhone(enteredPhone)
            typeTextInAddress(enteredAddress)
            typeTextInImageLink(enteredLink)
            pressBack()
            typeTextInScore(enteredRating)
            clickSubmitButton()
        }
        MainPage {
            checkNameInPosition(0, "$enteredName $enteredSurname")
            checkInfoInPosition(0, "Female, 24")
            checkEmailInPosition(0, enteredEmail)
            checkPhoneInPosition(0, enteredPhone)
            checkAddressInPosition(0, enteredAddress)
            checkRatingInPosition(0, enteredRating)
        }
    }

    @Test
    @AllureId("8")
    @DisplayName("Проверка отображения сообщения об ошибке")
    fun checkErrorMessageDisplay() = run {
        MainPage {
            clickButtonAddPerson()
            clickButtonManually()
        }
        EditStudentPage {
            clickSubmitButton()
            checkGenderErrorIsDisplayed()
        }
    }

    @Test
    @AllureId("9")
    @DisplayName("Проверка скрытия сообщения об ошибке при вводе данных в поле")
    fun checkErrorMessageIsHiddenWhenEnteringDataInField() = run {
        MainPage {
            clickButtonAddPerson()
            clickButtonManually()
        }
        EditStudentPage {
            typeTextInName(enteredName)
            typeTextInSurname(enteredSurname)
            replaceTextInGender(incorrectValue)
            typeTextInBirthday(enteredBirthday)
            typeTextInEmail(enteredEmail)
            typeTextInPhone(enteredPhone)
            typeTextInAddress(enteredAddress)
            typeTextInImageLink(enteredLink)
            pressBack()
            typeTextInScore(enteredRating)
            clickSubmitButton()
            checkGenderErrorIsDisplayed()
            clearTextInGender()
            checkGenderErrorIsNotDisplayed()
        }
    }

    companion object {
        private val enteredName = "Ann"
        private val enteredSurname = "Howard"
        private val enteredGender = "Ж"
        private val incorrectValue = "4"
        private val enteredBirthday = "2000-03-03"
        private val enteredEmail = "a@mail.ry"
        private val enteredPhone = "+796234529929"
        private val enteredAddress = "Moscow 9310"
        private val enteredRating = "44"
        private val enteredLink = "https://randomuser.me/api/portraits/women/61.jpg"
    }
}
