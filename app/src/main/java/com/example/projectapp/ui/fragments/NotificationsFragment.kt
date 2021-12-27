package com.example.projectapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectapp.R
import com.example.projectapp.databinding.FragmentNotificationsBinding
import com.example.projectapp.ui.activities.AddAdminActivity
import com.example.projectapp.ui.activities.AddProductActivity
import com.example.projectapp.ui.activities.SettingsActivity
import com.example.projectapp.ui.view_models.NotificationsViewModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) //Enabling the option menu
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    /**
     * Adds the 'Add Product' button to the fragment view.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_admin, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Sets the action of the 'Add Product' button
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId //the id of the pressed item
        when (id) {
            R.id.nav_add_admin -> {
                startActivity(Intent(activity, AddAdminActivity::class.java))
                return true
            }
            R.id.nav_client_board ->{
                startActivity(Intent(activity, SettingsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}