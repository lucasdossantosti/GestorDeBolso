package app.gb.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import app.gb.databinding.FragmentRecoverAccountBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RecoverAccountFragment : Fragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        initClicks()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initClicks(){
        binding.btnSend.setOnClickListener{
            validateData()
        }
    }

    private fun validateData(){
        val email = binding.editEmail.text.toString().trim()

        if(email.isNotEmpty()){
            binding.progreessBar.isVisible = true
            recoverAccountUser(email)

        }else if(email.isEmpty()){
            Toast.makeText(requireContext(), "Informe o seu e-mail.", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(requireContext(), "E-mail não encontrado!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recoverAccountUser(email:String){
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Se o e-mail inserido estiver cadastrado em nosso app um link de recuperação foi enviado para o seu e-mail, verifique sua caixa de entrada.", Toast.LENGTH_SHORT).show()
                }
                binding.progreessBar.isVisible = false
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}