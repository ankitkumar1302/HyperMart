package com.isodev.hypermart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TermsOfServiceScreen(
    onClickDisagree: () -> Unit,
    onClickAgree: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to DeepSeek",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Thank you for using DeepSeek. Please " +
                        "carefully read and fully understand the " +
                        "Terms of Use and Privacy Policy before " +
                        "clicking Agree. We will use your personal " +
                        "information in accordance with the agreed " +
                        "terms to provide our services.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = onClickDisagree,
                modifier = Modifier.weight(1f)
            ) {
                Text("Disagree")
            }

            Spacer(modifier = Modifier.weight(0.2f))

            Button(
                onClick = onClickAgree,
                modifier = Modifier.weight(1f)
            ) {
                Text("Agree")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TermsOfServiceScreenPreview() {
    MaterialTheme {
        TermsOfServiceScreen(
            onClickDisagree = {},
            onClickAgree = {}
        )
    }
}