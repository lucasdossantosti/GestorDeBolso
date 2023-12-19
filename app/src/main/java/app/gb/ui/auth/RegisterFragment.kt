package app.gb.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import app.gb.R
import app.gb.databinding.FragmentRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks(){
        binding.btnCriarConta.setOnClickListener{validateData()}
    }

    private fun validateData(){
        val nameCompany = binding.editnameCompany.text.toString().trim()
        val name = binding.editName.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editPassword.text.toString().trim()
        val confirmPassword = binding.editConfirmPassword.text.toString().trim()

        if(nameCompany.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() ){
            if(password == confirmPassword) {
                binding.progreessBar.isVisible = true
                registerUser(name, email, password);
            }else if(password.length < 6){
                Toast.makeText(requireContext(), "A senha precisa ter no mínimo 6 caracteres.", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "As senhas não são iguais, digite novamente.", Toast.LENGTH_SHORT).show()
            }
        }else if(nameCompany.isEmpty()){
            Toast.makeText(requireContext(), "Informe o nome da sua empresa.", Toast.LENGTH_SHORT).show()
        }else if(name.isEmpty()){
            Toast.makeText(requireContext(), "Informe o seu nome.", Toast.LENGTH_SHORT).show()
        }else if(email.isEmpty()){
            Toast.makeText(requireContext(), "Informe o seu e-mail.", Toast.LENGTH_SHORT).show()
        }else if(password.isEmpty()) {
            Toast.makeText(requireContext(), "Informe a sua senha.", Toast.LENGTH_SHORT).show()
        }else {
            Toast.makeText(requireContext(), "Confirme a sua senha.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(name: String, email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                   findNavController().navigate(R.id.action_global_homeFragment)
                } else {
                    binding.progreessBar.isVisible = false
                }
            }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}