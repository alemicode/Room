package com.example.r.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.r.R
import com.example.r.db.Note
import com.example.r.db.NoteDataBase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

/**
 * A simple [Fragment] subclass.
 */


class HomeFragment : Fragment() {


    private lateinit var job: CompletableJob
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        //check to see job has been initilized or not?
        if (!::job.isInitialized) {
            initJob()
        }


        btn_addNote.setOnClickListener {
            NavigateHandler(HomeFragmentDirections.actionAddNote(), it)
        }


        rcv_note.apply {
            rcv_note.setHasFixedSize(true)
            rcv_note.layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        }

        //get all notes
        CoroutineScope(IO + job).launch {
            val notes = NoteDataBase(activity!!).getNote().getAllNote()
            //display and update ui on MAIN Thread
            updateOnUIThread(notes)
        }




    }


    //display and update ui on MAIN Thread
    private fun updateOnUIThread(notes: List<Note>) {

        GlobalScope.launch(Main) {
            rcv_note.adapter = NotesAdapter(notes)
            resetJob(job)


        }
    }

    //reseting job after finish task
    private fun resetJob(job: CompletableJob) {
        if(job.isCompleted || job.isActive){
            job.cancel()
        }
    }


    //handle navigate between pages
    private fun NavigateHandler(action: NavDirections, it: View) {

        Navigation.findNavController(it).navigate(action)
    }


    private fun initJob() {
        job = Job()
        job.invokeOnCompletion {

            it?.let {
                var msg = it.message
                if (msg.isNullOrBlank()) {
                    msg = "job cancelled with no reason"
                }

                println("${msg}")
            }

            if (job.isCompleted) {

            }
        }


    }

    private fun showToast(msg: String, context: Context?) {

        GlobalScope.launch(Main) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }
    }


}
