package christoforos.list.presentation

import app.cash.turbine.test
import christoforos.common.domain.interactors.favorites.RemoveFavoriteUseCase
import christoforos.common.domain.models.event.Event
import christoforos.common.domain.models.outcome.Outcome
import christoforos.common.domain.models.outcome.OutcomeUtilities.toSuccessfulOutcome
import christoforos.list.R
import christoforos.list.domain.interactors.AddFavoriteUseCase
import christoforos.list.domain.interactors.GetSportsUseCase
import christoforos.list.domain.models.Sport
import christoforos.test.core.CoroutinesTestUtils.runTestWithDispatcher
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Test

internal class ListViewModelTest {

    private val getSportsUseCaseMock: GetSportsUseCase = mockk()
    private val addFavoriteUseCaseMock: AddFavoriteUseCase = mockk()
    private val removeFavoriteUseCaseMock: RemoveFavoriteUseCase = mockk()

    private val testedClass = ListViewModel(
        getSportsUseCaseMock,
        addFavoriteUseCaseMock,
        removeFavoriteUseCaseMock
    )

    @Test
    fun `when handling initialize event then get sports`() = runTestWithDispatcher {
        testedClass.sendEvent(ListContract.Event.Initialize)

        coVerify { getSportsUseCaseMock() }
    }

    @Test
    fun `when handling get data event then get sports`() = runTestWithDispatcher {
        testedClass.sendEvent(ListContract.Event.GetData)

        coVerify { getSportsUseCaseMock() }
    }

    @Test
    fun `when getting sports then set state to loading`() = runTestWithDispatcher {
        coEvery { getSportsUseCaseMock() } returns null.toSuccessfulOutcome()

        testedClass.state().test {
            testedClass.sendEvent(ListContract.Event.GetData)

            skipItems(1)
            val stateEmission = awaitItem().screenState
            skipItems(1)

            assertThat(stateEmission).isEqualTo(ListContract.ScreenState.Loading)
        }
    }

    @Test
    fun `given results when getting sports then set state to results`() = runTestWithDispatcher {
        val results = listOf<Sport>()
        coEvery { getSportsUseCaseMock() } returns results.toSuccessfulOutcome()

        testedClass.state().test {
            testedClass.sendEvent(ListContract.Event.GetData)

            skipItems(2)
            val stateEmission = awaitItem().screenState

            assertThat(stateEmission).isEqualTo(ListContract.ScreenState.Results(results))
        }
    }

    @Test
    fun `given null results when getting sports then set state to no results`() =
        runTestWithDispatcher {
            val results = null
            coEvery { getSportsUseCaseMock() } returns results.toSuccessfulOutcome()

            testedClass.state().test {
                testedClass.sendEvent(ListContract.Event.GetData)

                skipItems(2)
                val stateEmission = awaitItem().screenState

                assertThat(stateEmission).isEqualTo(ListContract.ScreenState.NoResults)
            }
        }

    @Test
    fun `given error when getting sports then set state to error`() = runTestWithDispatcher {
        coEvery { getSportsUseCaseMock() } returns Outcome.Error.GeneralError()

        testedClass.state().test {
            testedClass.sendEvent(ListContract.Event.GetData)

            skipItems(2)
            val stateEmission = awaitItem().screenState

            assertThat(stateEmission).isEqualTo(ListContract.ScreenState.Error)
        }
    }

    @Test
    fun `given favorite event when handling favorite event request event then add favorite event`() =
        runTestWithDispatcher {
            val event = getFakeEvent(true)

            testedClass.sendEvent(ListContract.Event.OnFavoriteEvent(event))

            coVerify { addFavoriteUseCaseMock(event) }
        }

    @Test
    fun `given not favorite event when handling favorite event request event then remove favorite event`() =
        runTestWithDispatcher {
            val event = getFakeEvent(false)

            testedClass.sendEvent(ListContract.Event.OnFavoriteEvent(event))

            coVerify { removeFavoriteUseCaseMock(event) }
        }

    @Test
    fun `given success when adding favorite then set added to favorites effect`() =
        runTestWithDispatcher {
            val event = getFakeEvent(true)
            coEvery { addFavoriteUseCaseMock(event) } returns null.toSuccessfulOutcome()

            testedClass.effect().test {
                testedClass.sendEvent(ListContract.Event.OnFavoriteEvent(event))

                val effectEmission = awaitItem()

                assertThat(effectEmission).isEqualTo(ListContract.Effect.AddedToFavorites)
            }
        }

    @Test
    fun `given error when adding favorite then show dialog with the correct message`() =
        runTestWithDispatcher {
            val event = getFakeEvent(true)
            coEvery { addFavoriteUseCaseMock(event) } returns Outcome.Error.GeneralError()

            testedClass.effect().test {
                testedClass.sendEvent(ListContract.Event.OnFavoriteEvent(event))

                val effectEmission = awaitItem()

                assertThat(effectEmission).isEqualTo(ListContract.Effect.ShowDialog(R.string.favorite_error))
            }
        }

    @Test
    fun `given error when removing favorite then show dialog`() =
        runTestWithDispatcher {
            val event = getFakeEvent(false)
            coEvery { removeFavoriteUseCaseMock(event) } returns Outcome.Error.GeneralError()

            testedClass.effect().test {
                testedClass.sendEvent(ListContract.Event.OnFavoriteEvent(event))

                val effectEmission = awaitItem()

                assertThat(effectEmission).isEqualTo(ListContract.Effect.ShowDialog(R.string.favorite_remove_error))
            }
        }

    private fun getFakeEvent(isFavorite: Boolean) =
        Event(null, null, null, null, null, isFavorite)
}

