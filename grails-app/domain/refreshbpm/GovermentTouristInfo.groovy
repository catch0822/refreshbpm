package refreshbpm

class GovermentTouristInfo {

    String name;
    String address;
    String city;
    String description;
    double lat;
    double lng;
    String imageUrl1;
    String imageUrl2;
    String imageUrl3;
    int positive = 0
    int negative = 0
    
    static constraints = {
        name(nullable: true)
        address(nullable: true)
        city(nullable: true)
        description(nullable: true)
        lat(nullable: true)
        lng(nullable: true)
        imageUrl1(nullable: true)
        imageUrl2(nullable: true)
        imageUrl3(nullable: true)        
    }
    
    static mapping = {
        description type: 'text'
    }  
    
}
