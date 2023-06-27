from selenium import webdriver
from selenium.webdriver.common.action_chains import ActionChains
from selenium.webdriver.common.keys import Keys

# Define the function to be called when the new button is clicked
def new_button_function():
    # Define the functionality of the new button here

    # For example, you could print a message to the console
    print("New button was clicked!")

# Instantiate the Selenium webdriver and navigate to the webpage
driver = webdriver.Chrome()
driver.get("https://veroviz.org/sketch.html")

input("type to exit")

# # Find an existing element on the page where you want to add the new button
# existing_element = driver.find_element_by_id("existing-element-id")

# # Create a new button element using JavaScript and execute it using Selenium
# new_button_script = """
# var newButton = document.createElement('button');
# newButton.innerHTML = 'New Button';
# newButton.onclick = function() {
#     // Call the function defined earlier when the new button is clicked
#     %s
# };
# arguments[0].appendChild(newButton);
# """ % new_button_function.__name__
# driver.execute_script(new_button_script, existing_element)

# # Wait for the new button to be added to the page
# driver.implicitly_wait(10)

# # Find the new button element and click it
# new_button = driver.find_element_by_xpath("//button[text()='New Button']")
# action = ActionChains(driver)
# action.move_to_element(new_button).click().perform()