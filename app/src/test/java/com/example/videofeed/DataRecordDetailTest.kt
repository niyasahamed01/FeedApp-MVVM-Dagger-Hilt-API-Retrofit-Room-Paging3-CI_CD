package com.example.videofeed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.videofeed.activity.DataRecordDetail
import com.example.videofeed.dao.Note
import com.example.videofeed.viewmodel.NoteViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class DataRecordDetailTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockViewModel: NoteViewModel

    @Captor
    private lateinit var noteCaptor: ArgumentCaptor<Note>

    private lateinit var activity: DataRecordDetail

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        activity = Robolectric.buildActivity(DataRecordDetail::class.java)
            .create()
            .resume()
            .get()
        activity.noteViewModel = mockViewModel
    }

    @Test
    fun testSaveNote() {
        // Given
        val title = "Test Title"
        val isLive = true

        // Simulate user input
        activity.binding.dataRecordRecord.setText(title)
        activity.binding.checkRecord.isChecked = isLive

        // When
        activity.saveNote(title, isLive) // Ensure this method triggers insert()

        // Then
        // Verify that the insert method was called with the expected argument
        verify(mockViewModel).insert(noteCaptor.capture())
        val capturedNote = noteCaptor.value

        // Check if capturedNote is null or not
        println("Captured Note: $capturedNote")

        // Validate the captured note
        assertNotNull("Captured note should not be null", capturedNote)
        assertEquals("Title should match", title, capturedNote.title)
        assertEquals("Live status should match", isLive, capturedNote.isLive)
    }
}