package com.alvaro.profile.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.alvaro.profile.R
import com.alvaro.profile.ui.theme.Gray
import com.alvaro.profile.ui.theme.LightPurple
import com.wiryadev.bootstrapiconscompose.BootstrapIcons
import com.wiryadev.bootstrapiconscompose.bootstrapicons.Normal
import com.wiryadev.bootstrapiconscompose.bootstrapicons.normal.Search

@Composable
fun SearchBar(
    searchText: TextFieldValue,
    onSearchTextChanged: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchText,
        onValueChange = onSearchTextChanged,
        placeholder = {
            Text(
                text = stringResource(id = R.string.search),
                color = Gray,
                fontWeight = FontWeight.Normal
            )
        },
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = BootstrapIcons.Normal.Search,
                contentDescription =  stringResource(id = R.string.search_icon),
                tint = Gray,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(22.dp),
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = LightPurple,
            cursorColor = Gray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = LightPurple,
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}