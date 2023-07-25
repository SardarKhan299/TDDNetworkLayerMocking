package com.raywenderlich.android.punchline

import io.reactivex.Single
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class JokeServiceTestMockingService {

    private val jokeService: JokeService = mock()
    private val repository = RepositoryImpl(jokeService)

    @Test
    fun getRandomJokeEmitJoke(){
        // 1
        val joke = Joke(id, joke)

        whenever(jokeService.getRandomJoke()).thenReturn(Single.just(joke))

        val testObserver = repository.getJoke().test()

        testObserver.assertValue(joke)
    }

}