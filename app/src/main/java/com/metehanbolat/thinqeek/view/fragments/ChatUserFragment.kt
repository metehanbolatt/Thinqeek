package com.metehanbolat.thinqeek.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.metehanbolat.thinqeek.R
import com.metehanbolat.thinqeek.adapter.ChatUserRecyclerAdapter
import com.metehanbolat.thinqeek.databinding.FragmentChatUserBinding
import com.metehanbolat.thinqeek.model.ChatUser
import com.metehanbolat.thinqeek.viewmodel.ChatUserFragmentViewModel

class ChatUserFragment : Fragment() {

    private var _binding : FragmentChatUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ChatUserFragmentViewModel
    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var chatList: ArrayList<ChatUser>
    private lateinit var chatAdapter: ChatUserRecyclerAdapter

    private var isWhat = ""
    private var contentTitle = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatUserBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ChatUserFragmentViewModel()
        firestore = Firebase.firestore
        auth = Firebase.auth
        chatList = ArrayList()
        chatAdapter = ChatUserRecyclerAdapter(requireContext(), chatList, viewModel)

        arguments?.let { bundle ->
            isWhat = ChatUserFragmentArgs.fromBundle(bundle).isWhat!!
            contentTitle = ChatUserFragmentArgs.fromBundle(bundle).contentTitle!!
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserComment(isWhat, contentTitle, firestore, chatList, chatAdapter)
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.chatRecyclerView.adapter = chatAdapter

        when(isWhat){
            "Movies" -> binding.movieOrSeriesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.movieColor))
            "Series" -> binding.movieOrSeriesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.seriesColor))
            "Technologies" -> binding.movieOrSeriesText.setTextColor(ContextCompat.getColor(requireContext(), R.color.newsColor))
        }

        binding.sendButton.setOnClickListener { buttonView ->
            if (binding.userChatEditText.text.toString() != ""){
                val comment = hashMapOf<String, Any>()
                comment["userDisplayName"] = auth.currentUser!!.displayName.toString()
                comment["userComment"] = binding.userChatEditText.text.toString()
                comment["date"] = Timestamp.now()

                firestore.collection(isWhat).document(contentTitle).collection("userComments").document(auth.currentUser!!.email.toString()).set(comment).addOnSuccessListener {
                    Snackbar.make(buttonView, "Yorumunuz başarıyla eklendi.", Snackbar.LENGTH_SHORT).show()
                    binding.userChatEditText.setText("")
                }.addOnFailureListener {
                    Snackbar.make(buttonView, "Bir sorun oluştu!", Snackbar.LENGTH_SHORT).show()
                }
            }else{
                Snackbar.make(buttonView, "Boş yorum yapamazsınız.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}