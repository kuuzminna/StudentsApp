package ru.tinkoff.favouritepersons.tests

//import ru.tinkoff.favouritepersons.data.network.Picture
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
            name.click()
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
            name.click()
        }
        EditStudentPage {
            replaceTextInName("Иосиф")
            clickSubmitButton()
        }
        MainPage {
            name.containsText("Иосиф Lampo")
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
            name.containsText("$enteredName $enteredSurname")
            info.containsText("Female, 24")
            email.containsText(enteredEmail)
            phone.containsText(enteredPhone)
            address.containsText(enteredAddress)
            rating.containsText(enteredRating)
            //TODO проверить картинку
            Thread.sleep(2_000)
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
            //TODO проверить что отображается рядом с полем ПОЛ
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
