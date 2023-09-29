package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.IntegrityCheckFailureException
import com.truck.monitor.app.data.InvalidResponseException
import com.truck.monitor.app.data.NoNetworkException
import com.truck.monitor.app.data.SearchResultNotFoundException
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ExceptionHandlerTest {
    private lateinit var sut: ExceptionHandler

    @Before
    fun setup() {
        sut = ExceptionHandler()
    }

    @Test
    fun `handle no network exception`() {
        val title = "Network Error"
        val description = "No Network Error Description"
        val noNetworkException = NoNetworkException(
            title = title,
            description = description
        )

        val result = sut.handle(noNetworkException)

        assertNotNull(result)
        assertEquals(title, result.message)
        assertEquals(description, result.description)
    }

    @Test
    fun `handle json parsing exception`() {
        val title = "Response Parsing Error"
        val description = "Couldn't parse the server response, please try later"
        val invalidResponseException = InvalidResponseException(
            title = title,
            description = description
        )

        val result = sut.handle(invalidResponseException)

        assertNotNull(result)
        assertEquals(title, result.message)
        assertEquals(description, result.description)
    }

    @Test
    fun `handle integrity check exception`() {
        val title = "Local Database Failure"
        val description = "Looks like database migration failed due to schema changes, please try later."
        val integrityCheckException = IntegrityCheckFailureException(
            title = title,
            description = description
        )

        val result = sut.handle(integrityCheckException)

        assertNotNull(result)
        assertEquals(title, result.message)
        assertEquals(description, result.description)
    }

    @Test
    fun `handle search not found exception`() {
        val title = "Search Result Not Found"
        val description = "Couldn't found any results against your query, try different query"
        val searchNotFoundException = SearchResultNotFoundException(
            title = title,
            description = description
        )

        val result = sut.handle(searchNotFoundException)

        assertNotNull(result)
        assertEquals(title, result.message)
        assertEquals(description, result.description)
    }


    @Test
    fun `handle uncaught exception`() {
        val title = "Unknown Error"
        val description = "Request failed due to unknown reasons, please try later."
        val result = sut.handle(TypeCastException())

        assertNotNull(result)
        assertEquals(title, result.message)
        assertEquals(description, result.description)
    }
}