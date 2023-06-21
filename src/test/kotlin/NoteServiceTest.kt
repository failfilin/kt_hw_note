import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.After

class NoteServiceTest {

    @Before
    fun setUp() {
    }
    @After
    fun tearDown() {
        NoteService.clear()
    }

    @Test
    fun add() {
        val note = Note(0, 1, 2, "Test notes")
        NoteService.add(note)
        val result = note.id
        assertNotEquals(0, result)
    }

    @Test
    fun deleteExisting() {
        val note = Note(0, 1, 2, "Test last")

        NoteService.add(note)

        val result = NoteService.delete(1)

        assertTrue(result)
    }

    @Test
    fun deleteNoteExisting() {
        val note = Note(0, 1, 2, "Test last")
        NoteService.add(note)
        val result = NoteService.delete(2)
        assertFalse(result)
    }

    @Test
    fun deleteCommentExisting() {
        val note = Note(0, 1, 2, "Test last")
        NoteService.add(note)
        val comment = Comment(0, 1, 2, "CommentOne")
        CommentService.createComment(1, comment)

        val result = NoteService.deleteComment(1)

        assertTrue(result)
    }

    @Test
    fun deleteCommentNotExisting() {
        val note = Note(0, 1, 2, "Test last")
        NoteService.add(note)
        val comment = Comment(0, 1, 2, "CommentOne")
        CommentService.createComment(1, comment)

        val result = NoteService.deleteComment(41)

        assertFalse(result)
    }

    @Test
    fun restoreCommentExisting() {
        val note = Note(0, 1, 2, "Test last")
        NoteService.add(note)
        val comment = Comment(0, 1, 2, "CommentOne")
        CommentService.createComment(1, comment)
        NoteService.deleteComment(1)
        val result = NoteService.restoreComment(1)
        assertTrue(result)
    }

    @Test
    fun restoreCommentNotExisting() {
        val note = Note(0, 1, 2, "Test last")
        NoteService.add(note)
        val comment = Comment(0, 1, 2, "CommentOne")
        CommentService.createComment(1, comment)
        NoteService.deleteComment(1)
        val result = NoteService.restoreComment(51)
        assertFalse(result)
    }
    @Test
    fun getExisting() {
        val note1 = Note(0, 1, 2, "Test last")
        // val note2 = Note(0, 21, 2, "Test next")
        NoteService.add(note1)
        //  NoteService.add(note2)
        NoteService.get()
        val result = note1.isDelete
        assertEquals(false, result)
    }
    @Test
    fun getNotExisting() {
        val notes = Note(0, 1, 2, "Test last")
        NoteService.add(notes)
        NoteService.delete(1)
        NoteService.get()
        val result = notes.id
        assertNotEquals(0, result)
    }
    @Test
    fun editExisting() {
        val note1 = Note(0, 1, 2, "Test last")
        val note2 = Note(0, 21, 2, "Test next")
        NoteService.add(note1)
        NoteService.add(note2)
        val editTestNote = Note(1, 4, 5, "Last, Next")
        val result = NoteService.edit(editTestNote)
        assertTrue(result)
    }
    @Test
    fun editNotExisting() {
        val note1 = Note(0, 1, 2, "Test last")
        val note2 = Note(0, 21, 2, "Test next")
        NoteService.add(note1)
        NoteService.add(note2)
        val editTestNote = Note(121, 4, 5, "Last, Next")
        val result = NoteService.edit(editTestNote)
        assertFalse(result)
    }
    @Test
    fun editCommentExisting() {
        val note1 = Note(0, 1, 2, "Test last")
        val note2 = Note(0, 21, 2, "Test next")
        NoteService.add(note1)
        NoteService.add(note2)
        val comment1 = Comment(0, 1, 2, "CommentOne")
        val comment2 = Comment(0, 1, 2, "CommentTwo")
        CommentService.createComment(1, comment1)
        CommentService.createComment(1, comment2)
        val editTestComment = Comment(1, 2, 3, "Comment comment")
        val result = NoteService.editComment(editTestComment)
        assertTrue(result)
    }
    @Test
    fun editCommentNotExisting() {
        val note1 = Note(0, 1, 2, "Test last")
        val note2 = Note(0, 21, 2, "Test next")
        NoteService.add(note1)
        NoteService.add(note2)
        val comment1 = Comment(0, 1, 2, "CommentOne")
        val comment2 = Comment(0, 1, 2, "CommentTwo")
        CommentService.createComment(1, comment1)
        CommentService.createComment(1, comment2)
        val editTestComment = Comment(12, 2, 3, "Comment comment")
        val result = NoteService.editComment(editTestComment)
        assertFalse(result)

    }

    @Test
    fun getById() {
    }

    @Test
    fun getComments() {
    }
}