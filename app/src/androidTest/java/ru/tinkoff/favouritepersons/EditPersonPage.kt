package ru.tinkoff.favouritepersons

import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

class EditPersonPage: BaseScreen() {
    val nameEdit = KEditText { withId(R.id.et_name) }
    val surnameEdit = KEditText { withId(R.id.et_surname) }
    val genderEdit = KEditText { withId(R.id.et_gender)}
    val birthdayEdit = KEditText { withId(R.id.et_birthdate) }
    val emailEdit = KEditText { withId(R.id.et_email) }
    val phoneEdit = KEditText { withId(R.id.et_phone) }
    val addressEdit = KEditText { withId(R.id.et_address) }
    val imageEdit = KEditText { withId(R.id.et_image) }
    val scoreEdit = KEditText { withId(R.id.et_score) }
    val submitButton = KButton { withId(R.id.submit_button) }

    val genderError = KTextView { withText("Поле должно быть заполнено буквами М или Ж")}

    fun typeTextInName(name:String) {
        nameEdit.typeText(name)
    }

    fun clickSubmitButton() {
        submitButton.click()
    }

}