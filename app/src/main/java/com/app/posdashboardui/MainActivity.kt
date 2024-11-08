package com.app.posdashboardui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.posdashboardui.ui.theme.POSDashboardUITheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            POSDashboardUITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    POSDashboardScreen()
                }
            }
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("TAG", "onSaveInstanceState: ${outState.size()}")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("TAG", "onRestoreInstanceState: ${savedInstanceState.size()}")
    }
}


/*@Composable
fun PaperCutBox(content: @Composable ColumnScope.() -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Draw the top and bottom paper-cut style
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            val path = Path()

            // Top cut style
            path.moveTo(0f, 50f)
            path.cubicTo(
                width * 0.25f, 0f,
                width * 0.75f, 100f,
                width, 50f
            )
            path.lineTo(width, 0f)
            path.lineTo(0f, 0f)
            path.close()

            // Draw the top path
            drawPath(
                path = path,
                color = Color.LightGray,
                style = Fill
            )

            // Reset path for bottom cut
            path.reset()

            // Bottom cut style
            path.moveTo(0f, height - 50f)
            path.cubicTo(
                width * 0.25f, height,
                width * 0.75f, height - 100f,
                width, height - 50f
            )
            path.lineTo(width, height)
            path.lineTo(0f, height)
            path.close()

            // Draw the bottom path
            drawPath(
                path = path,
                color = Color.LightGray,
                style = Fill
            )
        }

        // Content in the middle of the box
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 50.dp)
                .background(Color.White)
        ) {
            content()
        }
    }
}

@Composable
fun SampleScreen() {
    PaperCutBox {
        Text("Paper Cut Style", fontSize = 24.sp, modifier = Modifier.padding(16.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text("This is an example of a paper cut style box with content inside.")
    }
}*/

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val quantityPerUnit: Int
)


data class CartItem(
    val product: Product, var quantity: Int
) {
    val totalPrice: Double
        get() = product.price * quantity
}

@Composable
fun POSDashboardScreen() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SidebarNavigation()
        Spacer(modifier = Modifier.width(8.dp))
        ProductSelectionArea()
        Spacer(modifier = Modifier.width(8.dp))
        OrderSummaryArea()
    }
}

@Composable
fun SidebarNavigation() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(.04f),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Icons for navigation
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            IconButton(onClick = { /* Navigate to home */ }) {
                Icon(Icons.Default.Home, contentDescription = "Home")
            }
            IconButton(onClick = { /* Navigate to products */ }) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Products")
            }
            IconButton(onClick = { /* Navigate to settings */ }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
        }// Add more icons as needed
        Column(verticalArrangement = Arrangement.Bottom) {
            IconButton(onClick = { /* Navigate to products */ }) {
                Icon(Icons.Default.Person, contentDescription = "Account")
            }
            IconButton(onClick = { /* Navigate to settings */ }) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorite")
            }
        }
    }
}


@Composable
fun ProductSelectionArea() {
    val products = mutableListOf<Product>()
    products.add(Product("1", "Mango", "Summer season fruit", 12.3, "", 3))
    products.add(Product("2", "Mango", "Summer season fruit", 12.3, "", 3))
    products.add(Product("3", "Mango", "Summer season fruit", 12.3, "", 3))
    products.add(Product("4", "Mango", "Summer season fruit", 12.3, "", 3))
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxHeight(1f)
            .fillMaxWidth(.75f)
    ) {
        // Header
        Text("Welcome, Gory", style = MaterialTheme.typography.titleLarge)
        Text("Discover whatever you need easily")

        Spacer(modifier = Modifier.height(16.dp))

        // Category Tabs
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf("Signature", "Croissant", "Waffle", "Coffee", "Ice Cream").forEach { category ->
                Button(onClick = { /* Handle category selection */ }) {
                    Text(category)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(products) { _, product ->
                ProductCard(product)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(R.drawable.men),
                contentDescription = product.name,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(product.name, style = MaterialTheme.typography.titleMedium)
                    Text(product.description, style = MaterialTheme.typography.titleSmall)
                    Text(
                        "$${product.price}",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                // Add to Cart Button
                IconButton(
                    onClick = { /* Add to cart */ }, modifier = Modifier.padding(4.dp)
                ) {
                    Icon(Icons.Default.ShoppingCart, contentDescription = "Add to Cart")
                }
            }
        }
    }
}

@Composable
fun OrderSummaryArea() {
    val cartItems = mutableListOf<CartItem>()
    cartItems.add(CartItem(Product("1", "Mango", "Summer season fruit", 12.3, "", 3), 1))
    cartItems.add(CartItem(Product("2", "Mango", "Summer season fruit", 12.3, "", 3), 1))
    cartItems.add(CartItem(Product("3", "Mango", "Summer season fruit", 12.3, "", 3), 1))
    Column(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        Text("Current Order", style = MaterialTheme.typography.titleSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // List of ordered items
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(cartItems) { _, cartItem ->
                CartItemRow(cartItem)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Subtotal, Discounts, Taxes, and Total
        OrderSummaryDetails()

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Navigate to payment */ }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue to Payment")
        }
    }
}

@Composable
fun CartItemRow(cartItem: CartItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image
        Image(
            painter = painterResource(R.drawable.call),
            contentDescription = cartItem.product.name,
            modifier = Modifier.size(20.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth(.5f)
                .padding(horizontal = 10.dp)
        ) {
            Text(cartItem.product.name, style = MaterialTheme.typography.titleMedium)
            Text("$${cartItem.product.price}", style = MaterialTheme.typography.titleSmall)
        }

        // Quantity Controls
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { /* Decrease quantity */ }) {
                Icon(Icons.Default.Clear, contentDescription = "Decrease")
            }
            Text(cartItem.quantity.toString(), style = MaterialTheme.typography.titleLarge)
            IconButton(onClick = { /* Increase quantity */ }) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
        }
    }
}

@Composable
fun DashedDivider() {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
            .padding(vertical = 5.dp)
    ) {
        val dashWidth = 10f
        val gapWidth = 10f
        var startX = 0f
        while (startX < size.width) {
            drawLine(
                color = Color.Gray,
                start = Offset(x = startX, y = 0f),
                end = Offset(x = startX + dashWidth, y = 0f),
                strokeWidth = 2f
            )
            startX += dashWidth + gapWidth
        }
    }
}

@Composable
fun OrderSummaryDetails() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Subtotal: ")
            Text("$37.61")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Discount: ")
            Text("-$5.00")
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Sales Tax: ")
            Text("$2.25")
        }/*HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .wrapContentWidth(),
            thickness = 2.dp
        )*/
        DashedDivider()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "Total: ",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "$34.86",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@Preview(showSystemUi = true, device = Devices.TABLET,showBackground = true)
@Composable
fun Preview() {
    POSDashboardScreen()
}
