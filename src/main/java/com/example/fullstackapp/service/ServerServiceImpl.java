package com.example.fullstackapp.service;

import com.example.fullstackapp.enumeration.Status;
import com.example.fullstackapp.model.Server;
import com.example.fullstackapp.repository.ServerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Random;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ServerServiceImpl implements ServerService{

    private final ServerRepository serverRepository;

    @Override
    public Server ping(String ipAddress) throws IOException {
        log.info("Pinging server IP : {}",ipAddress);
        Server server = serverRepository.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
        server.setStatus(address.isReachable(10000) ? Status.Server_Up: Status.Server_Down);
        serverRepository.save(server);
        return server;
    }

    @Override
    public Server create(Server server) {
        log.info("Saving new Server : {}",server.getName());
        server.setImageUrl(this.setImgUrl());
        return serverRepository.save(server);

    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Fetching all servers");
        return serverRepository.findAll(PageRequest.of(0,limit)).toList();

    }

    @Override
    public Server get(Long id) {
        log.info("Fetching server id : {}",id);

        return serverRepository.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server  : {}",server.getName());
        return serverRepository.save(server);
    }

    @Override
    public boolean delete(Long id) {
        log.info("Deleting server id  : {}",id);
        serverRepository.deleteById(id);
        return true;
    }
    private String setImgUrl() {
        String [] images = { "server1.png","server2.png","server3.png","server4.png"};
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/server/image/"+images[new Random().nextInt(4)])
                .toUriString();
    }
}
