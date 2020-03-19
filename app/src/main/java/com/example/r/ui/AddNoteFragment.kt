package com.example.r.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation

import com.example.r.R
import com.example.r.db.Note
import com.example.r.db.NoteDataBase
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

/**
 * A simple [Fragment] subclass.
 */


class AddNoteFragment : Fragment() {

    private lateinit var job: CompletableJob


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)




        btn_save.setOnClickListener {
            if (!::job.isInitialized) {
                initJob()
            }
            addNote(job,it)


        }



    }


    //initilize job

    private fun initJob() {
        job = Job()
        job?.invokeOnCompletion {
            it?.let {

                var msg = it.message
                if (msg.isNullOrBlank()) {
                    msg = "cancell job with unknown error"
                }
                job.isCompleted.let {
                    if(it)
                        showToast("your note has been added successfuly",activity)
                }

            }


        }
    }

    private fun showToast(message: String, context: Context?) {
        GlobalScope.launch(Main) {
            Toast.makeText(context?.applicationContext, message, Toast.LENGTH_SHORT).show()

        }
    }


    private fun addNote(job: Job, it: View) {


        //get info from edit text

        var title = edt_title.text.toString().trim()
        var noteText = edt_note.text.toString().trim()


        //check to see if title is null or not
        if (title.isEmpty()) {
            edt_title.error = "title required"
            edt_title.requestFocus()
            return
        }

        //check to see if note is null or not
        if (noteText.isEmpty()) {
            edt_note.error = "title required"
            edt_note.requestFocus()
            return
        }

        //make note instance
        var note = Note(title, noteText)

        CoroutineScope(IO + job).launch {
            //connect to Dao
            NoteDataBase(activity!!).getNote().addNote(note)
            //reset job after get result
            resetJob(job)
            //back to main page after get the result
            backToView(it)
        }
    }

    private fun backToView(it: View) {

        GlobalScope.launch(Main) {
            Navigation.findNavController(it).navigate(R.id.action_saveNote)

        }
    }

    private fun resetJob(job : Job){

        if(job.isActive || job.isCompleted){
            job.cancel()
        }
    }




}
