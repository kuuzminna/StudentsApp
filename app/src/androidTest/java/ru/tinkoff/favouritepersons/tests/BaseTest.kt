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
import ru.tinkoff.favouritepersons.PersonDescription
import ru.tinkoff.favouritepersons.presentation.activities.MainActivity
import ru.tinkoff.favouritepersons.room.PersonDataBase
import ru.tinkoff.favouritepersons.utils.LocalhostPreferenceRule

open class BaseTest : TestCase() {

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
    }

    private fun buildResponseJson(person: PersonDescription): String {
        return JsonObject().apply {
            add(
                "results", JsonArray().apply {
                    add(person.toJson())
                }
            )
            add("info", JsonObject().apply {
                add("seed", JsonPrimitive("e4b2da62a533c3c5"))
                add("results", JsonPrimitive(1))
                add("page", JsonPrimitive(1))
                add("version", JsonPrimitive("1.4"))
            })
        }.toString()
    }
}