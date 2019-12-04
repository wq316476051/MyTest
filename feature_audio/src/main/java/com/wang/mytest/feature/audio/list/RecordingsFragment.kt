package com.wang.mytest.feature.audio.list

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wang.mytest.feature.audio.R
import com.wang.mytest.feature.audio.bean.Recording
import com.wang.mytest.feature.audio.playback.PlaybackFragment
import kotlinx.android.synthetic.main.fragment_record_list.*
import java.io.File

class RecordingsFragment : Fragment() {

    companion object {
        const val TAG = "RecordingsFragment"

        public fun newInstance(): Fragment {
            return RecordingsFragment()
        }
    }

    private lateinit var mViewModel: RecordingsViewModel

    private val mRecordingAdapter: RecordingsAdapter by lazy {
        RecordingsAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_record_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            it.title = "Recordings"
        }

        recycler_view.layoutManager = LinearLayoutManager(context!!, RecyclerView.VERTICAL, false)
        recycler_view.adapter = mRecordingAdapter

        btn_start_record.setOnClickListener {
            activity?.apply {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, RecordingsFragment.newInstance())
                        .addToBackStack("record")
                        .commit()
            }
        }

        mRecordingAdapter.setOnItemClickListener { recording, _ ->
            activity?.apply {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PlaybackFragment.newInstance(recording))
                        .addToBackStack("playback")
                        .commit()
            }
        }

        ViewModelProviders.of(activity!!).get(RecordingsViewModel::class.java).apply {
            mRecordings.observe(this@RecordingsFragment, Observer { it ->
                mRecordingAdapter.mRecordings = arrayListOf<Recording>().apply {
                    addAll(it)
                }
            })

            loadRecordings(true)
        }

        supportsPie {

        }
    }

    class RecordingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFilename = itemView.findViewById(R.id.tv_filename) as TextView
        val tvDuration = itemView.findViewById(R.id.tv_duration) as TextView
        val ibDelete = itemView.findViewById(R.id.ib_delete) as ImageButton
    }

    inner class RecordingsAdapter : RecyclerView.Adapter<RecordingsViewHolder>() {

        var mRecordings: ArrayList<Recording> = java.util.ArrayList()
            set(value) {
                field.clear()
                field.addAll(value)
            }

        private var mOnItemClickListener: ((Recording, Int) -> Unit)? = null

        fun setOnItemClickListener(listener: (recording: Recording, position: Int) -> Unit) {
            mOnItemClickListener = listener
        }

        override fun getItemCount(): Int = mRecordings.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordingsViewHolder {
            val view = layoutInflater.inflate(R.layout.item_recording, parent, false)
            return RecordingsViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecordingsViewHolder, position: Int) {
            mRecordings[position].also { recording ->
                holder.tvFilename.text = recording.filename
                holder.tvDuration.text = recording.duration.toString()
                holder.ibDelete.setOnClickListener {
                    mRecordings.removeAt(position)
                    mRecordingAdapter.notifyItemRemoved(position)
                    File(recording.filepath).delete()
                }
                holder.itemView.setOnClickListener {
                    mOnItemClickListener?.invoke(recording, position)
                }
            }
        }
    }

    inline fun supportsPie(code: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            code()
        }
    }
}