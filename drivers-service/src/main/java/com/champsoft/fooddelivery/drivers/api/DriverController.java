package com.champsoft.fooddelivery.drivers.api;

import com.champsoft.fooddelivery.drivers.api.dto.CreateDriverRequest;
import com.champsoft.fooddelivery.drivers.api.dto.DriverResponse;
import com.champsoft.fooddelivery.drivers.api.dto.UpdateDriverRequest;
import com.champsoft.fooddelivery.drivers.api.mapper.DriverApiMapper;
import com.champsoft.fooddelivery.drivers.application.service.DriverCrudService;
import com.champsoft.fooddelivery.drivers.domain.model.Driver;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverCrudService driverCrudService;
    private final DriverApiMapper driverApiMapper;

    public DriverController(DriverCrudService driverCrudService, DriverApiMapper driverApiMapper) {
        this.driverCrudService = driverCrudService;
        this.driverApiMapper = driverApiMapper;
    }

    @GetMapping
    public ResponseEntity<List<DriverResponse>> getAll() {
        return ResponseEntity.ok(driverCrudService.getAll().stream().map(driverApiMapper::toResponse).toList());
    }

    @GetMapping("/{driverId}")
    public ResponseEntity<DriverResponse> getById(@PathVariable UUID driverId) {
        return ResponseEntity.ok(driverApiMapper.toResponse(driverCrudService.getById(driverId)));
    }

    @PostMapping
    public ResponseEntity<DriverResponse> create(@Valid @RequestBody CreateDriverRequest request) {
        Driver driver = driverCrudService.create(request.name(), request.phone(), request.status());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(driver.getId())
                .toUri();
        return ResponseEntity.created(location).body(driverApiMapper.toResponse(driver));
    }

    @PutMapping("/{driverId}")
    public ResponseEntity<DriverResponse> update(@PathVariable UUID driverId, @Valid @RequestBody UpdateDriverRequest request) {
        return ResponseEntity.ok(driverApiMapper.toResponse(driverCrudService.update(
                driverId,
                request.name(),
                request.phone(),
                request.status()
        )));
    }

    @DeleteMapping("/{driverId}")
    public ResponseEntity<Void> delete(@PathVariable UUID driverId) {
        driverCrudService.delete(driverId);
        return ResponseEntity.noContent().build();
    }
}
