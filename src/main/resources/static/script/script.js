console.log("script loaded");

let currentTheme=getTheme();
changeTheme();


//to change the current theme which is set  to light
function changeTheme(){
//setting theme to webpage
document.querySelector("html").classList.add(currentTheme);
const changeThemeButton = document.querySelector("#theme_change_button");
changeThemeButton.addEventListener("click", (event) => {
    //removing current theme
    document.querySelector('html').classList.remove(currentTheme);
    console.log("change theme button clicked");
    if (currentTheme === "dark"){
        currentTheme = "light";
    }
    else {
        currentTheme = "dark";
    }
    //changed theme ko set krnege
    setTheme(currentTheme);
    document.querySelector('html').classList.add(currentTheme);
    changeThemeButton.querySelector('span').textContent = currentTheme == "light" ? "Dark" : "Light";
});
}

//to set the cuurent theme to local storage which is changed
function setTheme(theme){
    localStorage.setItem("theme",theme);
}

//get theme from local storage
function getTheme(){
    let theme=localStorage.getItem("theme")
    if(theme)
        return theme;
    else
        return "light";
}