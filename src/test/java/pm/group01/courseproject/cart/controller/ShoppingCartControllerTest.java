package pm.group01.courseproject.cart.controller;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pm.group01.courseproject.cart.model.*;
import pm.group01.courseproject.cart.repository.ShoppingCartRepository;
import pm.group01.courseproject.cart.service.ShoppingCartService;
import pm.group01.courseproject.securityconfig.jwt.userdeatils.service.UserDetailsServiceImp;
import pm.group01.courseproject.securityconfig.jwt.util.JwtUtil;
import pm.group01.courseproject.user.model.EndUser;
import pm.group01.courseproject.user.service.EndUserService;

import java.util.ArrayList;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class ShoppingCartControllerTest {
    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    EndUserService endUserService;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @MockBean
    ShoppingCartService shoppingCartService;

    @Autowired
    private WebApplicationContext wac;

    public static MockMvc mockMvc;
    private final static String GetItemsURI = "/cartLine/api/v1/getCartItems";

    private final static String AddItemUri = "/cartLine/api/v1/add";
    private final static String DeleteItemUri = "/cartLine/api/v1/delete";
    private final static String EditItemUri = "/cartLine/api/v1/edit";

    EndUser endUser;
    @BeforeAll
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
         endUser = endUserService.findEndUserByEmail("someshTest@yahoo.com");
        if(endUser==null) {
            endUser = new EndUser();
            endUser.setEmail("someshTest@yahoo.com");
            endUser.setName("someshTest");
            endUser.setUsername("someshTest");
            endUser.setPassword("someshTest");
            endUserService.saveEndUser(endUser);
        }

    }
//////////////////////////////
    /*
    getItems Test Cases
     */

    @Test
    void getItemsListWithNonEmptyCartLine()throws Exception  {

        CartLine cartLineUi1= new CartLine();
        cartLineUi1.setCategoryId(1);
        cartLineUi1.setImageLink("http//kk.jpg");
        cartLineUi1.setPrice(30);
        cartLineUi1.setProductDesc("chipsy");
        cartLineUi1.setProductId(Long.parseLong("20"));
        cartLineUi1.setQuantity(30);
        cartLineUi1.setSellerId(30);
        cartLineUi1.setCategoryName("accessories");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setEndUserId(endUser.getId());
        cartLineUi1.setCart(shoppingCart);
        List<CartLine> cartLineUiList = new ArrayList<>();
        cartLineUiList.add(cartLineUi1);
        shoppingCart.setCartLineList(cartLineUiList);

        shoppingCartRepository.save(shoppingCart);
        given(shoppingCartService.findAllByID(endUser.getId())).willReturn(cartLineUiList);

        // when + then

        mockMvc.perform(get(GetItemsURI).header("Authorization", obtainAccessToken("someshTest","someshTest"))
        ).andExpect(status().isOk())
                .andExpect(content().json("[{'quantity':30,'price':30,'sellerId':30,'imageLink':'http//kk.jpg','categoryName':'accessories','productDesc':'chipsy','productId':20}]"));
shoppingCartRepository.delete(shoppingCart);

    }

    @Test
    void getItemsListWithEmptyCartLines()throws Exception  {

        List<CartLine> cartLineUiList = new ArrayList<>();

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setEndUserId(endUser.getId());
        shoppingCart.setCartLineList(cartLineUiList);

        given(shoppingCartService.findAllByID(endUser.getId())).willReturn(cartLineUiList);

        // when + then

        mockMvc.perform(get(GetItemsURI).header("Authorization", obtainAccessToken("someshTest","someshTest"))
        ).andExpect(status().isOk())
                .andExpect(content().json("[]"));
        shoppingCartRepository.delete(shoppingCart);
    }


    ///////////////////////
    /*
    Add Test Functionality
    *Add to emptyShopping Cart
    *add to existed product in Your ShoppingCart
     */
    @Test
    void AddToEmptyShoppingCart()throws Exception {
        CartLine cartLineUi1= new CartLine();
        cartLineUi1.setCategoryId(1);
        cartLineUi1.setImageLink("http//kk.jpg");
        cartLineUi1.setPrice(30);
        cartLineUi1.setProductDesc("chipsy");
        cartLineUi1.setProductId(Long.parseLong("20"));
        cartLineUi1.setQuantity(30);
        cartLineUi1.setSellerId(30);
        cartLineUi1.setCategoryName("accessories");

        given(shoppingCartService.save(cartLineUi1,endUser.getId())).willReturn(cartLineUi1);
        mockMvc.perform(post(AddItemUri).header("Authorization", obtainAccessToken("someshTest","someshTest")).contentType(MediaType.APPLICATION_JSON).content(asJsonString(new CartLineUi(cartLineUi1.getProductId(),cartLineUi1.getQuantity(),cartLineUi1.getProductDesc(),cartLineUi1.getPrice(),cartLineUi1.getProductName(),cartLineUi1.getCategoryId(),cartLineUi1.getImageLink(),cartLineUi1.getSellerId(),cartLineUi1.getCategoryName())))
        ).andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void AddToExistedProductInShoppingCart()throws Exception {
        CartLine cartLineUi1= new CartLine();
        cartLineUi1.setCategoryId(1);
        cartLineUi1.setImageLink("http//kk.jpg");
        cartLineUi1.setPrice(30);
        cartLineUi1.setProductDesc("chipsy");
        cartLineUi1.setProductId(Long.parseLong("20"));
        cartLineUi1.setQuantity(30);
        cartLineUi1.setSellerId(30);
        cartLineUi1.setCategoryName("accessories");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setEndUserId(endUser.getId());
        cartLineUi1.setCart(shoppingCart);
        List<CartLine> cartLineUiList = new ArrayList<>();
        cartLineUiList.add(cartLineUi1);
        shoppingCart.setCartLineList(cartLineUiList);
        CartLine cartLineUi2= new CartLine();
        cartLineUi2.setCategoryId(1);
        cartLineUi2.setImageLink("http//kk.jpg");
        cartLineUi2.setPrice(30);
        cartLineUi2.setProductDesc("chipsy");
        cartLineUi2.setProductId(Long.parseLong("20"));
        cartLineUi2.setQuantity(40);
        cartLineUi2.setSellerId(30);
        cartLineUi2.setCategoryName("accessories");

        CartLine cartLineUi3= new CartLine();
        cartLineUi3.setCategoryId(1);
        cartLineUi3.setImageLink("http//kk.jpg");
        cartLineUi3.setPrice(30);
        cartLineUi3.setProductDesc("chipsy");
        cartLineUi3.setProductId(Long.parseLong("20"));
        cartLineUi3.setQuantity(70);
        cartLineUi3.setSellerId(30);
        cartLineUi3.setCategoryName("accessories");

        shoppingCartRepository.save(shoppingCart);
        given(shoppingCartService.save(cartLineUi2,endUser.getId())).willReturn(cartLineUi3);
        mockMvc.perform(post(AddItemUri).header("Authorization", obtainAccessToken("someshTest","someshTest")).contentType(MediaType.APPLICATION_JSON).content(asJsonString(new CartLineUi(cartLineUi2.getProductId(),cartLineUi2.getQuantity(),cartLineUi2.getProductDesc(),cartLineUi2.getPrice(),cartLineUi2.getProductName(),cartLineUi2.getCategoryId(),cartLineUi2.getImageLink(),cartLineUi2.getSellerId(),cartLineUi2.getCategoryName())))
        ).andExpect(status().isOk())
                .andReturn();
        shoppingCartRepository.delete(shoppingCart);

    }



    /*
    Remove  Test
    *remove from existing Shopping Cart
    *remove from Non existing shopping Cart
     */
    @Test
    void removeExistedCartLine()throws Exception {
        CartLine cartLineUi1= new CartLine();
        cartLineUi1.setCategoryId(1);
        cartLineUi1.setImageLink("http//kk.jpg");
        cartLineUi1.setPrice(30);
        cartLineUi1.setProductDesc("chipsy");
        cartLineUi1.setProductId(Long.parseLong("20"));
        cartLineUi1.setQuantity(30);
        cartLineUi1.setSellerId(30);
        cartLineUi1.setCategoryName("accessories");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setEndUserId(endUser.getId());
        cartLineUi1.setCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        DeletedCartLine deletedCartLine = new DeletedCartLine();
        deletedCartLine.setProductId(cartLineUi1.getProductId());

        given(shoppingCartService.DeleteCart(deletedCartLine,endUser.getId())).willReturn(true);
        mockMvc.perform(delete(DeleteItemUri).header("Authorization", obtainAccessToken("someshTest","someshTest")).contentType(MediaType.APPLICATION_JSON).content(asJsonString(new DeletedCartLine(deletedCartLine.getProductId())))
        ).andExpect(status().isOk())
                .andReturn();
        shoppingCartRepository.delete(shoppingCart);


    }
    @Test
    void removeNonExistedCartLine()throws Exception {
        DeletedCartLine deletedCartLine = new DeletedCartLine();
        deletedCartLine.setProductId(Long.parseLong("20"));

        given(shoppingCartService.DeleteCart(deletedCartLine,endUser.getId())).willReturn(false);
        mockMvc.perform(delete(DeleteItemUri).header("Authorization", obtainAccessToken("someshTest","someshTest")).contentType(MediaType.APPLICATION_JSON).content(asJsonString(new DeletedCartLine(deletedCartLine.getProductId())))
        ).andExpect(status().isBadRequest())
                .andReturn();
    }


    /*
    Edit Test
    *Edit existing cartLine
    *Edit Non existing cartLine
     */
    @Test
    void EditExistedCartLine()throws Exception {
        CartLine cartLineUi1= new CartLine();
        cartLineUi1.setCategoryId(1);
        cartLineUi1.setImageLink("http//kk.jpg");
        cartLineUi1.setPrice(30);
        cartLineUi1.setProductDesc("chipsy");
        cartLineUi1.setProductId(Long.parseLong("20"));
        cartLineUi1.setQuantity(30);
        cartLineUi1.setSellerId(30);
        cartLineUi1.setCategoryName("accessories");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setEndUserId(endUser.getId());
        cartLineUi1.setCart(shoppingCart);
        shoppingCartRepository.save(shoppingCart);
        UpdatedCartLine updatedCartLine = new UpdatedCartLine();
        updatedCartLine.setProductId(cartLineUi1.getProductId());
        updatedCartLine.setQuantity(20);

        given(shoppingCartService.updateCart(updatedCartLine,endUser.getId())).willReturn(true);
        mockMvc.perform(put(EditItemUri).header("Authorization", obtainAccessToken("someshTest","someshTest")).contentType(MediaType.APPLICATION_JSON).content(asJsonString(new UpdatedCartLine(updatedCartLine.getProductId(),updatedCartLine.getQuantity())))
        ).andExpect(status().isOk())
                .andReturn();
        shoppingCartRepository.delete(shoppingCart);


    }
    @Test
    void EditNonExistedCartLine()throws Exception {

        UpdatedCartLine updatedCartLine = new UpdatedCartLine();
        updatedCartLine.setProductId(Long.parseLong("20"));
        updatedCartLine.setQuantity(20);
        given(shoppingCartService.updateCart(updatedCartLine,endUser.getId())).willReturn(false);
        mockMvc.perform(put(EditItemUri).header("Authorization", obtainAccessToken("someshTest","someshTest")).contentType(MediaType.APPLICATION_JSON).content(asJsonString(new UpdatedCartLine(updatedCartLine.getProductId(),updatedCartLine.getQuantity())))
        ).andExpect(status().isBadRequest())
                .andReturn();
    }



    public  String obtainAccessToken(String username, String password) throws Exception {
        userDetailsService.setUsertype("enduser");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    username, password));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

         String jwt = jwtTokenUtil.generateToken(userDetails);
        return  jwt;
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}