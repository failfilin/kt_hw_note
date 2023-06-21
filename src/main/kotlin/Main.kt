abstract class BaseService<T> {
    val item = mutableListOf<T>()
    open fun add(note: T) {}
    abstract fun delete(id: Int): Boolean
    abstract fun deleteComment(id: Int): Boolean
    abstract fun edit(note: T): Boolean
    open fun editComment(comment: T) {}
    open fun get(note: T) {}
    abstract fun getById(id: Int): Boolean
    open fun getComments(note: T) {}
    abstract fun restoreComment(id: Int): Boolean
}

data class Comment(
    var id: Int,
    val ownerId: Int,
    val date: Int,
    val text: String,
    var isDelete: Boolean = false
)

object CommentService {
    fun createComment(noteId: Int, comment: Comment): Comment {
        for (note in NoteService.item) {
            if (noteId == note.id) {
                if (note.comments.isEmpty()) {
                    comment.id = 1
                } else {
                    comment.id = note.comments.last().id + 1
                }
                note.comments += comment
                return note.comments.last()
            }
        }
        throw PostNotFoundException("Заметка с id = $noteId не найдена")
    }
}

data class Note(
    var id: Int,
    val ownerId: Int,
    val date: Int,
    val text: String,
    var isDelete: Boolean = false,
    var comments: MutableList<Comment> = mutableListOf()
)

object NoteService : BaseService<Note>() {
    override fun add(note: Note) {
        if (item.isEmpty()) {
            note.id = 1
        } else {
            note.id = item.last().id + 1
        }
        item += note
    }

    override fun delete(id: Int): Boolean {
        for ((index, note) in item.withIndex()) {
            if (index == id - 1) {
                val deleteNotes = note.copy(isDelete = true)
                item[index] = deleteNotes
                return true
            }
        }
        return false
    }

    override fun deleteComment(id: Int): Boolean {
        for (note in item)
            for ((index, comment) in note.comments.withIndex())
                if (index == id - 1) {
                    val deleteComments = comment.copy(isDelete = true)
                    note.comments[index] = deleteComments
                    return true
                }
        return false
    }

    override fun restoreComment(id: Int): Boolean {
        for (note in item)
            for ((index, comment) in note.comments.withIndex())
                if (comment.id == id)
                    if (comment.isDelete) {
                        val restoreComments = comment.copy(isDelete = false)
                        note.comments[index] = restoreComments
                        return true
                    }
        return false
    }

    fun get() {
        for (note in item) {
            if (note.isDelete) {
                println("Заметка $note.id была удалена")
            } else {
                println(note.text + " " + note.id)
            }
        }
    }

    override fun edit(note: Note): Boolean {
        for ((index, editNote) in item.withIndex()) {
            if (editNote.id == note.id) {
                item[index] = note.copy(ownerId = note.ownerId, date = note.date)
                return true
            }
        }
        return false
    }

    fun editComment(comment: Comment): Boolean {
        for (note in item)
            for ((index, editNote) in note.comments.withIndex()) {
                if (editNote.id == comment.id) {
                    note.comments[index] = comment.copy(ownerId = comment.ownerId, date = comment.date)
                    return true
                }
            }
        return false
    }

    override fun getById(id: Int): Boolean {
        for (note in item) {
            if (note.id == id) {
                return true
            }
        }
        return false
    }

    fun getComments() {
        for (note in item)
            for (comment in note.comments) {
                if (comment.isDelete == true) {
                    println("Комментарий был удален")
                } else {
                    println(comment.text + " " + comment.id)
                }
            }
    }

    fun clear() {
        item.clear()
    }
}

class PostNotFoundException(message: String) : RuntimeException(message)

fun main() {}