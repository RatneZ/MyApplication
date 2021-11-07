package com.ratnez.myapplication.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.ratnez.myapplication.R
import com.ratnez.myapplication.common.*
import com.ratnez.myapplication.databinding.ItemPersonBinding
import com.ratnez.myapplication.model.Person

class PersonAdapter constructor(var context: Context, var onActionListener: OnActionListener) :
        RecyclerView.Adapter<PersonAdapter.ViewHolder>() {
    private var list = listOf<Person>()

    fun setDataAndNotify(list: List<Person>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.tvName.text = "${name.title} ${name.first} ${name.last}, ${dob.age}"
                binding.tvCity.text = "${location.city}, ${location.country}"
                binding.ivImage.load(picture.large) {
                    crossfade(true)
                    transformations(RoundedCornersTransformation())
                }

                when (userAction) {
                    ACTION_NONE -> {
                        binding.rlUserAction.setBackgroundColor(context.resources.getColor(android.R.color.white))
                        binding.llAction.visible()
                        binding.tvUserAction.gone()
                    }
                    ACTION_ACCEPTED -> {
                        binding.rlUserAction.setBackgroundColor(context.resources.getColor(android.R.color.holo_green_light))
                        binding.llAction.gone()
                        binding.tvUserAction.visible()
                        binding.tvUserAction.text = context.getString(R.string.message_accepted)
                    }
                    ACTION_REJECTED -> {
                        binding.rlUserAction.setBackgroundColor(context.resources.getColor(android.R.color.holo_red_light))
                        binding.llAction.gone()
                        binding.tvUserAction.visible()
                        binding.tvUserAction.text = context.getString(R.string.message_declined)
                    }
                }

                binding.btnAccept.setOnClickListener { onActionListener.onAccepted(this, position) }
                binding.btnDecline.setOnClickListener { onActionListener.onDeclined(this, position) }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnActionListener {
        fun onAccepted(person: Person, position: Int)
        fun onDeclined(person: Person, position: Int)
    }
}