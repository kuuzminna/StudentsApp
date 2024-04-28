package ru.tinkoff.favouritepersons.utils

import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import io.github.kakaocup.kakao.text.KTextView
import io.qameta.allure.kotlin.Allure.step
import org.hamcrest.Matchers
import ru.tinkoff.favouritepersons.R

class PersonViewElement(private val position: Int) {

    private val rootView = ViewMatchers.isDescendantOfA(
        Matchers.allOf(
            ViewMatchers.withParent(ViewMatchers.withId(R.id.rv_person_list)),
            ViewMatchers.withParentIndex(position)
        )
    )

    private val name = KTextView(rootView) { withId(R.id.person_name) }
    private val info = KTextView(rootView) { withId(R.id.person_private_info) }
    private val email = KTextView(rootView) { withId(R.id.person_email) }
    private val phone = KTextView(rootView) { withId(R.id.person_phone) }
    private val address = KTextView(rootView) { withId(R.id.person_address) }
    private val rating = KTextView(rootView) { withId(R.id.person_rating) }

    fun checkEmail(text: String) {
        step("Проверить, что эмейл для студента в позиции = ${position + 1} состоит из значения \"$text\"") {
            email.hasText(text)
        }
    }

    fun checkInfo(text: String) {
        step("Проверить, что блок информаци для студента в позиции = ${position + 1} состоит из значения \"$text\"") {
            info.containsText(text)
        }
    }

    fun checkPhone(text: String) {
        step("Проверить, что телефон для студента в позиции = ${position + 1} состоит из значения \"$text\"") {
            phone.hasText(text)
        }
    }

    fun checkAddress(text: String) {
        step("Проверить, что адресс для студента в позиции = ${position + 1} состоит из значения \"$text\"") {
            address.containsText(text)
        }
    }

    fun checkRating(text: String) {
        step("Проверить, что рейтинг для студента в позиции = ${position + 1} состоит из значения \"$text\"") {
            rating.hasText(text)
        }
    }

    fun checkName(text: String) {
        step("Проверить, что имя для студента в позиции = ${position + 1} состоит из значения \"$text\"") {
            name.containsText(text)
        }
    }

    fun swipePerson() {
        step("Удалить студента в позиции = ${position + 1}") {
            name.act {
                ViewActions.swipeLeft()
            }
        }
    }
    fun clickPerson() {
        step("Тапнуть на студента в позиции = ${position + 1}") {
            name.click()
        }
    }
}