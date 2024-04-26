package ru.tinkoff.favouritepersons.tests

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.junit.WireMockRule
import com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.RuleChain
import ru.tinkoff.favouritepersons.LocalhostPreferenceRule
import ru.tinkoff.favouritepersons.PersonDescription
import ru.tinkoff.favouritepersons.presentation.activities.MainActivity
import ru.tinkoff.favouritepersons.room.PersonDataBase

open class BaseTest: TestCase() {

    @get:Rule
    val activityRule =
        RuleChain.outerRule(LocalhostPreferenceRule())
            .around(WireMockRule(5000))
            .around(ActivityScenarioRule(MainActivity::class.java))

    private lateinit var db: PersonDataBase

    @Before
    fun createDb() {
        db = PersonDataBase.getDBClient(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @After
    fun clearDB() {
        db.personsDao().clearTable()
    }

    fun mock(personList: List<PersonDescription>) {
//        if (personList.size == 1) {
//            val person = personList[0]
//            stubFor(
//                get(urlEqualTo("/api/"))
//                    .willReturn(
//                        aResponse()
//                            .withBody(buildResponseJson(person))
//                    )
//            )
//        }
//        else {
            for (i in personList.indices) {
                val person = personList[i]
                stubFor(
                    get(urlEqualTo("/api/"))
                        .inScenario("")
                        .whenScenarioStateIs(if (i == 0) STARTED else "Step $i")
                        .willSetStateTo("Step ${i + 1}")
                        .willReturn(
                            aResponse()
                                .withBody(buildResponseJson(person))
                        )
                )
            }
//        }
    }

    private fun buildResponseJson(person: PersonDescription): String {
         return JsonObject().apply {
            add("results", JsonArray().apply {
                add(person.toJson())
//                add(JsonObject().apply {
//                    add("gender", JsonPrimitive(person.gender))
//                    add("name", JsonObject().apply {
//                        add("title", JsonPrimitive(person.title))
//                        add("first", JsonPrimitive(person.name))
//                        add("last", JsonPrimitive(person.lastname))
//                    })
//                    add("location", JsonObject().apply {
//                        add("street", JsonObject().apply {
//                            add(
//                                "number",
//                                JsonPrimitive(person.location.number)
//                            )
//                            add("name", JsonPrimitive(person.location.name))
//                        })
//                        add("city", JsonPrimitive(person.location.city))
//                        add("state", JsonPrimitive(person.location.state))
//                        add(
//                            "country",
//                            JsonPrimitive(person.location.country)
//                        )
//                        add("postcode", JsonPrimitive(17225))
//                        add("coordinates", JsonObject().apply {
//                            add("latitude", JsonPrimitive("-24.2735"))
//                            add("longitude", JsonPrimitive("-64.5634"))
//                        })
//                        add("timezone", JsonObject().apply {
//                            add("offset", JsonPrimitive("-4:00"))
//                            add(
//                                "description",
//                                JsonPrimitive("Atlantic Time (Canada), Caracas, La Paz")
//                            )
//                        })
//                    })
//                    add("email", JsonPrimitive(person.email))
//                    add("login", JsonObject().apply {
//                        add(
//                            "uuid",
//                            JsonPrimitive("874391a1-88af-4f1f-b459-91f283bdbd5c")
//                        )
//                        add("username", JsonPrimitive("goldenmeercat659"))
//                        add("password", JsonPrimitive("doggy1"))
//                        add("salt", JsonPrimitive("IIO07d8o"))
//                        add(
//                            "md5",
//                            JsonPrimitive("8fd03a7d6879044d3dfa6515e819c8b3")
//                        )
//                        add(
//                            "sha1",
//                            JsonPrimitive("12262b076714ebea5cdcd8e6c5daf1f5fe0145b9")
//                        )
//                        add(
//                            "sha256",
//                            JsonPrimitive("35ae8f6ca3112e739a05343f824e2fbb33e54fd1a0681ff5d2b83590016f035c")
//                        )
//                    })
//                    add("dob", JsonObject().apply {
//                        add(
//                            "date",
//                            JsonPrimitive("1951-08-01T10:34:45.721Z")
//                        )
//                        add("age", JsonPrimitive(person.age))
//                    })
//                    add("registered", JsonObject().apply {
//                        add(
//                            "date",
//                            JsonPrimitive("2021-06-26T11:59:14.081Z")
//                        )
//                        add("age", JsonPrimitive(2))
//                    })
//                    add("phone", JsonPrimitive(person.phone))
//                    add("cell", JsonPrimitive("048-033-31-18"))
//                    add("id", JsonObject().apply {
//                        add("name", JsonPrimitive("HETU"))
//                        add("value", JsonPrimitive("NaNNA421undefined"))
//                    })
//                    add("picture", JsonObject().apply {
//                        val url = "https://randomuser.me/api/portraits/"
//                        add("large", JsonPrimitive("${url}$person.picture"))
//                        add(
//                            "medium",
//                            JsonPrimitive("${url}med/$person.picture")
//                        )
//                        add(
//                            "thumbnail",
//                            JsonPrimitive("${url}thumb/$person.picture")
//                        )
//                    })
//                    add("nat", JsonPrimitive("FI"))
//                            person.toJson()
//                }
//                )
            })
            add("info", JsonObject().apply {
                add("seed", JsonPrimitive("e4b2da62a533c3c5"))
                add("results", JsonPrimitive(1))
                add("page", JsonPrimitive(1))
                add("version", JsonPrimitive("1.4"))
            })
        }.toString()
    }
}