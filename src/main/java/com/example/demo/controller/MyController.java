package com.example.demo.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import src.App;
class MyRequest {
    private double distanceArray[][];
    public double[][] getDistanceArray() {
        return distanceArray;
    }

    public void setDistanceArray(double[][] distanceArray) {
        this.distanceArray = distanceArray;
    }



    // Getters and setters


}
class MyResponse {
    private int[][] routes;
    private int status;
    private double cost;
    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public int[][] getRoutes() {
        return routes;
    }

    public void setRoutes(int[][] routes) {
        this.routes = routes;
    }


}
@RestController
public class MyController {
    @GetMapping("/")
    public String index() {
        double ans= App.runner();
        return String.valueOf(ans);
    }

    @PostMapping("/")
    public ResponseEntity<MyResponse> handleRequest(@RequestBody MyRequest request) {
        // Access the properties of the request object
        double[][] arr2d =request.getDistanceArray();
        // Perform additional processing or tap into the data
        for(double i[]:arr2d){
            for(double j:i){
                System.out.print(j+" ");
            }
            System.out.println();
        }
        App.setDistanceMatrix(arr2d);
        double ans= App.runner();
        int[][] routes=App.getRoutes();
        MyResponse response = new MyResponse();
        response.setRoutes(routes);
        response.setStatus(200);
        response.setCost(ans);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
