package ru.tinkoff.favouritepersons.pages

import io.github.kakaocup.kakao.check.KCheckBox
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KSnackbar
import io.github.kakaocup.kakao.text.KTextView
import io.qameta.allure.kotlin.Allure.step
import ru.tinkoff.favouritepersons.R

class MainPage: BasePage() {
    private val text = KTextView { withId(R.id.tw_no_persons) }
    private val buttonAddPerson = KButton { withId(R.id.fab_add_person)}
    private val buttonNetwork = KButton { withId(R.id.fab_add_person_by_network) }
    private val buttonManual = KButton { withId(R.id.fab_add_person_manually) }
    private val buttonSort = KButton { withId(R.id.action_item_sort) }
    private val ymolch = KCheckBox { withId(R.id.bsd_rb_default) }
    private val age = KCheckBox { withId(R.id.bsd_rb_age) }
    private val viewKSnackbar = KSnackbar()
    private val snackbarKView = KView { withText(textOfSnackbar) }

    val name = KTextView { withId(R.id.person_name)}
    val info = KTextView { withId(R.id.person_private_info) }
    val email = KTextView { withId(R.id.person_email) }
    val phone = KTextView { withId(R.id.person_phone) }
    val address = KTextView { withId(R.id.person_address) }
    val rating = KTextView { withId(R.id.person_rating) }

    fun checkTextIsDisplayed() {
        step("Проверить, что текст \"\" отображается") {
            text.isDisplayed()
        }
    }

    fun checkTextIsNotDisplayed() {
        step("Проверить, что текст \"\" не отображается") {
            text.isNotDisplayed()
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
            ymolch.isChecked()
        }
    }

    fun clickAge() {
        step("Тап на сортировку по возрасту") {
            age.click()
        }
    }

    fun checkSnackbarDisplayed() {
        step("Проверить, что снекбар с текстом \"$textOfSnackbar\" отображается") {
            snackbarKView.isDisplayed()
            viewKSnackbar.isDisplayed()
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