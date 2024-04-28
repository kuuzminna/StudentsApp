package ru.tinkoff.favouritepersons.pages

import io.github.kakaocup.kakao.check.KCheckBox
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KSnackbar
import io.github.kakaocup.kakao.text.KTextView
import io.qameta.allure.kotlin.Allure.step
import ru.tinkoff.favouritepersons.R
import ru.tinkoff.favouritepersons.utils.PersonViewElement

class MainPage : BasePage() {
    private val textNoPersons = KTextView { withId(R.id.tw_no_persons) }
    private val buttonAddPerson = KButton { withId(R.id.fab_add_person) }
    private val buttonNetwork = KButton { withId(R.id.fab_add_person_by_network) }
    private val buttonManual = KButton { withId(R.id.fab_add_person_manually) }
    private val buttonSort = KButton { withId(R.id.action_item_sort) }
    private val defaultCheckbox = KCheckBox { withId(R.id.bsd_rb_default) }
    private val ageCheckbox = KCheckBox { withId(R.id.bsd_rb_age) }
    private val viewKSnackbar = KSnackbar()
    private val snackbarKView = KView { withText(textOfSnackbar) }
    private val recyclerView = KRecyclerView ({ withId(R.id.rv_person_list) }, {})

    fun checkTextNoPersonsIsDisplayed() {
        step("Проверить, что текст об отсутствии студентов в списке отображается") {
            textNoPersons.isDisplayed()
        }
    }

    fun checkTextNoPersonsIsNotDisplayed() {
        step("Проверить, что текст об отсутствии студентов в списке не отображается") {
            textNoPersons.isNotDisplayed()
        }
    }

    fun clickButtonAddPerson() {
        step("Тап на кнопку добавления человека") {
            buttonAddPerson.click()
        }
    }

    fun clickButtonNetwork() {
        step("Тап на кнопку добавления из сети") {
            buttonNetwork.click()
        }
    }

    fun clickButtonManually() {
        step("Тап на кнопку добавления вручную") {
            buttonManual.click()
        }
    }

    fun clickSortButton() {
        step("Тап на кнопку сортировки") {
            buttonSort.click()
        }
    }

    fun checkIsChecked() {
        step("Проверить, что выбрана сортировка по умолчанию") {
            defaultCheckbox.isChecked()
        }
    }

    fun clickAge() {
        step("Тап на сортировку по возрасту") {
            ageCheckbox.click()
        }
    }

    fun checkSnackbarDisplayed() {
        step("Проверить, что снекбар с текстом \"$textOfSnackbar\" отображается") {
            snackbarKView.isDisplayed()
            viewKSnackbar.isDisplayed()
        }
    }

    fun clickPersonInPosition(position: Int = 0) {
        PersonViewElement(position).clickPerson()
    }

     fun checkInfoInPosition(position: Int = 0, age: String) {
         PersonViewElement(position).checkInfo(age)
     }

    fun swipePersonInPosition(position: Int = 0) {
        PersonViewElement(position).swipePerson()
    }

    fun checkEmailInPosition(position: Int = 0, text: String) {
        PersonViewElement(position).checkEmail(text)
    }

    fun checkPhoneInPosition(position: Int = 0, text: String) {
        PersonViewElement(position).checkPhone(text)
    }

    fun checkAddressInPosition(position: Int = 0, text: String) {
        PersonViewElement(position).checkAddress(text)
    }

    fun checkRatingInPosition(position: Int = 0, text: String) {
        PersonViewElement(position).checkRating(text)
    }

    fun checkNameInPosition(position: Int = 0, text: String) {
        PersonViewElement(position).checkName(text)
    }

    fun scroolToEnd() {
        step("Проскролллить до конца списка студентов") {
            recyclerView.scrollToEnd()
        }
    }

    companion object {
        private val textOfSnackbar: String = "Internet error! Check your connection"

        inline operator fun invoke(crossinline block: MainPage.() -> Unit) =
            step("Основная страница") {
                MainPage().block()
            }
    }
}