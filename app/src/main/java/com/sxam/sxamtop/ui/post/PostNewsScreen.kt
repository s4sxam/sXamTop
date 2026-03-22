package com.sxam.sxamtop.ui.post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sxam.sxamtop.ui.theme.TealAccent
import com.sxam.sxamtop.utils.Constants // FIX: Imported Constants

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostNewsScreen(
    viewModel: PostNewsViewModel = hiltViewModel() // FIX: Replaced parameterless ViewModel with Hilt
) {
    val uiState by viewModel.uiState.collectAsState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var source by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") } // FIX: Added state for Image URL
    
    // FIX: Using Constants instead of locally declared list
    var selectedCategory by remember { mutableStateOf(Constants.POST_CATEGORIES.first()) }
    var categoryExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isSubmitted) {
        if (uiState.isSubmitted) {
            // Clear fields on successful submit
            title = ""; description = ""; source = ""; url = ""; imageUrl = ""
            selectedCategory = Constants.POST_CATEGORIES.first()
            viewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post News", style = MaterialTheme.typography.titleLarge) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description *") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 6
            )
            OutlinedTextField(
                value = source,
                onValueChange = { source = it },
                label = { Text("Source Name *") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            ExposedDropdownMenuBox(
                expanded = categoryExpanded,
                onExpandedChange = { categoryExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = categoryExpanded,
                    onDismissRequest = { categoryExpanded = false }
                ) {
                    Constants.POST_CATEGORIES.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = { selectedCategory = cat; categoryExpanded = false }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("Article URL (optional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // FIX: Allow user to attach an image URL to their post
            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Image URL (optional)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            uiState.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = { 
                    viewModel.submitPost(
                        title = title, 
                        description = description, 
                        source = source, 
                        category = selectedCategory, 
                        url = url,
                        imageUrl = imageUrl
                    ) 
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TealAccent)
            ) {
                Text("Submit Post", color = Color.Black)
            }
        }
    }
}