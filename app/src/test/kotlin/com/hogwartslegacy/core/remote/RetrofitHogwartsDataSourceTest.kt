package com.hogwartslegacy.core.remote

import com.hogwartslegacy.core.data.model.HogwartsCharacter
import com.hogwartslegacy.core.data.remote.FatalException
import com.hogwartslegacy.core.data.remote.HogwartsService
import com.hogwartslegacy.core.data.remote.RetrofitHogwartsDataSource
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class RetrofitHogwartsDataSourceTest {

    private val character = mockk<HogwartsCharacter>()
    private val characterResponse = mockk<Response<List<HogwartsCharacter>>> {
        every { body() } returns listOf(character)
        every { isSuccessful } returns true
    }
    private val service = mockk<HogwartsService> {
        coEvery { getCharacters() } returns characterResponse
    }

    private val remoteDataSource = RetrofitHogwartsDataSource(service)

    @Test
    fun `Given data is available when getCharacters called then character list is returned`() =
        runTest {
            val result = remoteDataSource.getCharacters()
            assertEquals(listOf(character), result)
        }

    @Test(expected = FatalException::class)
    fun `Given data is not available when getCharacters called then exception is thrown`() {
        val characterResponse = mockk<Response<List<HogwartsCharacter>>> {
            every { body() } returns null
            every { isSuccessful } returns true
        }
        coEvery { service.getCharacters() } returns characterResponse
        runTest {
            remoteDataSource.getCharacters()
            fail("Should have thrown FatalException")
        }
    }

    @Test(expected = FatalException::class)
    fun `Given request is not successful when getCharacters called then exception is thrown`() {
        val characterResponse = mockk<Response<List<HogwartsCharacter>>> {
            every { isSuccessful } returns false
        }
        coEvery { service.getCharacters() } returns characterResponse
        runTest {
            remoteDataSource.getCharacters()
            fail("Should have thrown FatalException")
        }
    }

    @Test(expected = FatalException::class)
    fun `Given network failed when getCharacters called then exception is thrown`() {
        val characterResponse = mockk<Response<List<HogwartsCharacter>>> {
            every { body() } throws IOException()
            every { isSuccessful } returns true
        }
        coEvery { service.getCharacters() } returns characterResponse
        runTest {
            remoteDataSource.getCharacters()
            fail("Should have thrown FatalException")
        }
    }
}