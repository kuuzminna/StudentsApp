package ru.tinkoff.favouritepersons

import io.github.kakaocup.kakao.check.KCheckBox
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
//import io.qameta.allure.kotlin.Allure.step

class MainPage: BaseScreen() {
    private val text = KTextView { withId(R.id.tw_no_persons) }
    private val buttonAddPerson = KButton { withId(R.id.fab_add_person)}
    private val buttonNetwork = KButton { withId(R.id.fab_add_person_by_network) }
    private val buttonManual = KButton { withId(R.id.fab_add_person_manually) }
    private val buttonSort = KButton { withId(R.id.action_item_sort) }
    private val ymolch = KCheckBox { withId(R.id.bsd_rb_default) }
    private val age = KCheckBox { withId(R.id.bsd_rb_age) }


    fun checkTextIsDisplayed() {
//        step("") {
            text.isDisplayed()
//        }
    }
    fun clickButtonAddPerson() {
//        step("") {
            buttonAddPerson.click()
//        }
    }
    fun clickButtonNetwork() {
//        step("") {
            buttonNetwork.click()
//        }
    }

    fun clickButtonManually() {
        buttonManual.click()
    }
    fun checkTextIsNotDisplayed() {
//        step("") {
            text.isNotDisplayed()
//        }
    }

    fun clickSortButton() {
        buttonSort.click()
    }

    fun checkIsChecked() {
        ymolch.isChecked()
    }

    fun clickAge() {
        age.click()
    }
}