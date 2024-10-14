Feature: Ride creation and management

  Scenario: Create a new ride
    Given A driver with id 100, firstName "Nikita", carId 1, email "driver@gmail.com", phone "+3465454545"
    And A passenger with email "passenger@gmail.com", phone "+3465454545", firstName "ABOBA", secondName "ABOBUM"
    When I send a request to create a ride
    Then The ride should be successfully created


  Scenario: Accepted ride
    Given A driver with id 100, firstName "Nikita", carId 1, email "driver@gmail.com", phone "+3465454545"
    And A passenger with email "passenger@gmail.com", phone "+3465454545", firstName "ABOBA", secondName "ABOBUM"
    When I send a request to create a ride
    And The driver accepted the ride
    Then The ride should be have ACCEPTED status and startDate

  Scenario: Test charge money
    Given A driver with id 100, firstName "Nikita", carId 1, email "driver@gmail.com", phone "+3465454545"
    And A passenger with email "passenger@gmail.com", phone "+3465454545", firstName "ABOBA", lastName "ABOBUM", and balance 120
    And I send a request to create a ride with a price of 100
    And The driver accepted the ride
    When The driver completes the ride
    Then The passenger's balance should be 20
