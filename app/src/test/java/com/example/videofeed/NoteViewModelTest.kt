package com.example.videofeed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.videofeed.dao.Note
import com.example.videofeed.dao.NoteRepository
import com.example.videofeed.viewmodel.NoteViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NoteViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: NoteRepository

    private lateinit var viewModel: NoteViewModel

    @Captor
    private lateinit var noteCaptor: ArgumentCaptor<Note>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

}