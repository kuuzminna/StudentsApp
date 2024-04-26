package ru.tinkoff.favouritepersons

import android.content.Context
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
//import com.github.tomakehurst.wiremock.client.WireMock.matching
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KSnackbar
import io.github.kakaocup.kakao.text.KTextView
import io.qameta.allure.kotlin.junit4.DisplayName
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
//import ru.tinkoff.favouritepersons.data.network.Picture
import ru.tinkoff.favouritepersons.domain.Gender
import ru.tinkoff.favouritepersons.presentation.activities.MainActivity
import ru.tinkoff.favouritepersons.room.PersonDataBase

class Tests: TestCase() {

    class LocalhostPreferenceRule: TestRule {
        override fun apply(base: Statement, description: Description): Statement {
            return object : Statement(){
                override fun evaluate() {
                    setStringPrefParam(spFileName, spParamUrlName, spParamAddress)
                    setStringPrefParam(spFileName, spWikiUrlName, spParamAddress)
                    base.evaluate()
                    clearPreferences(spFileName)
                }

            }
        }
        companion object {
            private val spFileName = "demo_url"
            private val spParamUrlName = "url"
            private val spWikiUrlName = "wiki_url"
            private val spParamAddress = "http://localhost:5000/"
        }

        private fun setStringPrefParam(prefName: String, param: String, value: String) {
            val pref = InstrumentationRegistry.getInstrumentation().targetContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString(param, value)
            editor.commit()
        }

        private fun clearPreferences(prefName: String) {
            val pref = InstrumentationRegistry.getInstrumentation().targetContext.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.clear()
            editor.commit()
        }
    }

    @get:Rule
    val activityRule =
        RuleChain.outerRule(LocalhostPreferenceRule())
            .around(WireMockRule(5000))
            .around(ActivityScenarioRule(MainActivity::class.java))
////        ActivityScenarioRule(MainActivity::class.java)

    private lateinit var db: PersonDataBase

    @Before
    fun createDb() {
        db = PersonDataBase.getDBClient(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @After
    fun clearDB() {
        db.personsDao().clearTable()
    }

    @Test
    //TODO Добавить нормально аннотации аллюр
    @DisplayName("Проверка скрытия сообщения об отсутствии студентов")
    fun test1() = run {
        //TODO сделать нормально мокирование человечка
        mock(
            PersonDescription()
        )
        with(MainPage()) {
            checkTextIsDisplayed()
            clickButtonAddPerson()
            clickButtonNetwork()
            checkTextIsNotDisplayed()
            Thread.sleep(2000)
        }
    }

    class PersonDescription(
        val name: String = "Leo",
        val lastname: String = "Lampo",
        val title: String = "Mr",
        val gender: String = Gender.MALE.toString(),
        val location: Address = Address(),
        val email: String = "leo.lampo@example.com",
        val phone:String = "06-278-783",
        val age:Int = 72,
        val picture: String = "men/13.jpg"
    ) {
        class Address(
            val number: Int = 2194,
            val name: String = "Pyynikintie",
            val city: String = "Hammarland",
        val state: String = "Satakunta",
        val country: String = "Finland",
        )
        fun toJson() : JsonObject = JsonObject().apply {
            add("gender", JsonPrimitive(gender))
            add ("name", JsonObject().apply {
                add("title", JsonPrimitive(title))
                add("first", JsonPrimitive(name))
                add("last", JsonPrimitive(lastname))
            })
            add("location", JsonObject().apply {
                add("street", JsonObject().apply {
                    add("number", JsonPrimitive(location.number))
                    add("name", JsonPrimitive(location.name))
                })
                add("city", JsonPrimitive(location.city))
                add("state", JsonPrimitive(location.state))
                add("country", JsonPrimitive(location.country))
                add("postcode", JsonPrimitive(17225))
                add("coordinates", JsonObject().apply {
                    add("latitude", JsonPrimitive("-24.2735"))
                    add("longitude", JsonPrimitive("-64.5634"))
                })
                add("timezone", JsonObject().apply {
                    add("offset", JsonPrimitive("-4:00"))
                    add("description", JsonPrimitive("Atlantic Time (Canada), Caracas, La Paz"))
                })
            })
            add("email", JsonPrimitive(email))
            add("login", JsonObject().apply {
                add("uuid", JsonPrimitive("874391a1-88af-4f1f-b459-91f283bdbd5c"))
                add("username", JsonPrimitive("goldenmeercat659"))
                add("password", JsonPrimitive("doggy1"))
                add("salt", JsonPrimitive("IIO07d8o"))
                add("md5", JsonPrimitive("8fd03a7d6879044d3dfa6515e819c8b3"))
                add("sha1", JsonPrimitive("12262b076714ebea5cdcd8e6c5daf1f5fe0145b9"))
                add("sha256", JsonPrimitive("35ae8f6ca3112e739a05343f824e2fbb33e54fd1a0681ff5d2b83590016f035c"))
            })
            add("dob", JsonObject().apply {
                add("date", JsonPrimitive("1951-08-01T10:34:45.721Z"))
                add("age", JsonPrimitive(age))
            })
            add("registered", JsonObject().apply {
                add("date", JsonPrimitive("2021-06-26T11:59:14.081Z"))
                add("age", JsonPrimitive(2))
            })
            add("phone", JsonPrimitive(phone))
            add("cell", JsonPrimitive("048-033-31-18"))
            add("id", JsonObject().apply {
                add("name", JsonPrimitive("HETU"))
                add("value", JsonPrimitive("NaNNA421undefined"))
            })
            add("picture", JsonObject().apply {
                val url = "https://randomuser.me/api/portraits/"
                add("large", JsonPrimitive("${url}$picture"))
                add("medium", JsonPrimitive("${url}med/$picture"))
                add("thumbnail", JsonPrimitive("${url}thumb/$picture"))
            })
            add("nat", JsonPrimitive("FI"))
        }
}

    @Test
//    @DisplayName("Проверка удаления студента")
    fun test2() = run {
        mock(PersonDescription())
        mock(
            PersonDescription(
                name = "Anton",
                lastname = "MMMs",
                age = 44
            )
        )
        mock(
            PersonDescription(
                name = "Kiril",
                lastname = "MDS",
                age = 22
            )
        )
        with(MainPage()) {
            clickButtonAddPerson()
            repeat(3) { clickButtonNetwork() }
            //TODO свайпнуть кого-то
            //TODO проверить удаление
        }
    }

    @Test
    @DisplayName("Проверка выбора по умолчанию в окне сортировки")
    fun test3() = run {
        with(MainPage()) {
            clickSortButton()
            checkIsChecked()
        }
    }

    @Test
//    @DisplayName("Проверка сортировки по возрасту")
    fun test4() = run {
        //TODO мокнуть трех людей
        with(MainPage()) {
            clickButtonAddPerson()
            repeat(3) { clickButtonNetwork() }
            clickSortButton()
            clickAge()
            //TODO сделать проверку сортировки по возрастанию
        }
    }

    @Test
    //    @DisplayName("Проверка открытия второго экрана с данными пользователя")
    fun test5() = run {
        with(MainPage()) {
            clickButtonAddPerson()
             clickButtonNetwork()
            name.click()
        }
        with(EditPersonPage()) {
            nameEdit.hasText("Leo")
            surnameEdit.hasText("Lampo")
            genderEdit.hasText(Gender.MALE.toString())
            birthdayEdit.hasText("") //TODO как узнать дату рождения
        }

    }

    @Test
    //    @DisplayName("Проверка редактирования студента")
    fun test6() = run {
        with(MainPage()) {
            clickButtonNetwork()
            name.click()
        }
    with(EditPersonPage()) {
        typeTextInName("Иосиф")
        clickSubmitButton()
    }
        name.containsText("Иосиф Lampo")
}

    val name = KTextView { withId(R.id.person_name)}

    @Test
//    @DisplayName("Проверка добавления студента")
    fun test7() = run {
        with(MainPage()) {
            clickButtonAddPerson()
            clickButtonManually()
        }
        with(EditPersonPage()) {
            val enteredName = "Ann"
            val enteredSurname = "Howard"
            val enteredEmail = "a@mail.ry"
            val enteredPhone = "+796234529929"
            val enteredAddress = "Moscow 9310"
            val enteredRating = "44"
            nameEdit.typeText(enteredName)
            surnameEdit.typeText(enteredSurname)
            genderEdit.replaceText("Ж")
            birthdayEdit.typeText("2000-03-03")
            emailEdit.typeText(enteredEmail)
            phoneEdit.typeText(enteredPhone)
            addressEdit.typeText(enteredAddress)
            imageEdit.typeText("https://randomuser.me/api/portraits/women/61.jpg")
            pressBack()
            scoreEdit.typeText(enteredRating)
            submitButton.click()
//            Thread.sleep(45_000)
            val name = KTextView { withId(R.id.person_name)}
            val info = KTextView { withId(R.id.person_private_info) }
            val email = KTextView { withId(R.id.person_email) }
            val phone = KTextView { withId(R.id.person_phone) }
            val address = KTextView { withId(R.id.person_address) }
            val rating = KTextView { withId(R.id.person_rating) }
            name.containsText("$enteredName $enteredSurname")
            info.containsText("Female, 24")
            email.containsText(enteredEmail)
            phone.containsText(enteredPhone)
            address.containsText(enteredAddress)
            rating.containsText(enteredRating)
            //TODO проверить картинку
            //TODO почему-то акцент на корректный возраст
            Thread.sleep(2_000)
        }
    }

    @Test
//    @DisplayName("Проверка отображения сообщения об ошибке")
    fun test8() = run {
        with(MainPage()) {
            clickButtonAddPerson()
            clickButtonManually()
        }
        with(EditPersonPage()) {
            clickSubmitButton()
            genderError.isDisplayed()
            //TODO проверить что отображается рядом с полем ПОЛ
        }
    }



    @Test
//    @DisplayName("Проверка скрытия сообщения об ошибке при вводе данных в поле")
    fun test9() = run {
        with(MainPage()) {
            clickButtonAddPerson()
            clickButtonManually()
        }
        with(EditPersonPage()) {
            val enteredName = "Ann"
            val enteredSurname = "Howard"
            val enteredEmail = "a@mail.ry"
            val enteredPhone = "+796234529929"
            val enteredAddress = "Moscow 9310"
            val enteredRating = "44"
            nameEdit.typeText(enteredName)
            surnameEdit.typeText(enteredSurname)
            genderEdit.replaceText("4")
            birthdayEdit.typeText("2000-03-03")
            emailEdit.typeText(enteredEmail)
            phoneEdit.typeText(enteredPhone)
            addressEdit.typeText(enteredAddress)
            imageEdit.typeText("https://randomuser.me/api/portraits/women/61.jpg")
            pressBack()
            scoreEdit.typeText(enteredRating)
            submitButton.click()
            genderError.isDisplayed()
            genderEdit.clearText()
            genderError.doesNotExist()
        }

    }

    @Test
    //    @DisplayName("Проверка отображения сообщения об ошибке интернет-соединения")
    fun test10() = run {
//        stubFor(WireMock.get(WireMock.urlPathMatching("/api/"))
//            .willReturn(aResponse()
//                .withStatus(500)))
        //TODO сделать мок с ошибкой
        with(MainPage()) {
            clickButtonAddPerson()
            clickButtonNetwork()
            val snakbar = KSnackbar()
            KView { withText("Internet error! Check your connection") }.isDisplayed()
            snakbar.isDisplayed() //TODO определить одну проверку
        }
    }

    private fun mock(person: PersonDescription) {
//        val wireMockServer = WireMockServer()
//        wireMockServer.start()
//        configureFor("localhost", 5000);
//        stubFor(WireMock.get(urlEqualTo("/baeldung")).willReturn(aResponse().withBody("Welcome to Baeldung!")))

        stubFor(WireMock.get(WireMock.urlPathMatching("/api/"))
//            .withHeader("Accept", matching("text/.*"))
            .willReturn(aResponse()
                .withStatus(200)
//                .withHeader(" content-type", "application/json")
                .withBody(
                    JsonObject().apply {
                        add("results", JsonArray().apply {
                            //TODO
                            add("gender", JsonPrimitive(person.gender))
                            add ("name", JsonObject().apply {
                                add("title", JsonPrimitive(person.title))
                                add("first", JsonPrimitive(person.name))
                                add("last", JsonPrimitive(person.lastname))
                            })
                            add("location", JsonObject().apply {
                                add("street", JsonObject().apply {
                                    add("number", JsonPrimitive(person.location.number))
                                    add("name", JsonPrimitive(person.location.name))
                                })
                                add("city", JsonPrimitive(person.location.city))
                                add("state", JsonPrimitive(person.location.state))
                                add("country", JsonPrimitive(person.location.country))
                                add("postcode", JsonPrimitive(17225))
                                add("coordinates", JsonObject().apply {
                                    add("latitude", JsonPrimitive("-24.2735"))
                                    add("longitude", JsonPrimitive("-64.5634"))
                                })
                                add("timezone", JsonObject().apply {
                                    add("offset", JsonPrimitive("-4:00"))
                                    add("description", JsonPrimitive("Atlantic Time (Canada), Caracas, La Paz"))
                                })
                            })
                            add("email", JsonPrimitive(person.email))
                            add("login", JsonObject().apply {
                                add("uuid", JsonPrimitive("874391a1-88af-4f1f-b459-91f283bdbd5c"))
                                add("username", JsonPrimitive("goldenmeercat659"))
                                add("password", JsonPrimitive("doggy1"))
                                add("salt", JsonPrimitive("IIO07d8o"))
                                add("md5", JsonPrimitive("8fd03a7d6879044d3dfa6515e819c8b3"))
                                add("sha1", JsonPrimitive("12262b076714ebea5cdcd8e6c5daf1f5fe0145b9"))
                                add("sha256", JsonPrimitive("35ae8f6ca3112e739a05343f824e2fbb33e54fd1a0681ff5d2b83590016f035c"))
                            })
                            add("dob", JsonObject().apply {
                                add("date", JsonPrimitive("1951-08-01T10:34:45.721Z"))
                                add("age", JsonPrimitive(person.age))
                            })
                            add("registered", JsonObject().apply {
                                add("date", JsonPrimitive("2021-06-26T11:59:14.081Z"))
                                add("age", JsonPrimitive(2))
                            })
                            add("phone", JsonPrimitive(person.phone))
                            add("cell", JsonPrimitive("048-033-31-18"))
                            add("id", JsonObject().apply {
                                add("name", JsonPrimitive("HETU"))
                                add("value", JsonPrimitive("NaNNA421undefined"))
                            })
                            add("picture", JsonObject().apply {
                                val url = "https://randomuser.me/api/portraits/"
                                add("large", JsonPrimitive("${url}$person.picture"))
                                add("medium", JsonPrimitive("${url}med/$person.picture"))
                                add("thumbnail", JsonPrimitive("${url}thumb/$person.picture"))
                            })
                            add("nat", JsonPrimitive("FI"))
//                            person.toJson()
                            //TODO
                        })
                        add("info", JsonObject().apply {
                            add("seed", JsonPrimitive("e4b2da62a533c3c5"))
                            add("results", JsonPrimitive(1))
                            add("page", JsonPrimitive(1))
                            add("version", JsonPrimitive("1.4"))
                        })
                    }.toString()
                )))
//        wireMockServer.stop()
    }
}