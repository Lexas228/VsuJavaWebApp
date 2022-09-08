package ru.vsu.app.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import liquibase.util.StringUtil;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.stereotype.Component;
import ru.vsu.app.webapp.dto.PlayerDto;
import ru.vsu.app.webapp.service.PlayerService;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
@ConditionalOnNotWebApplication
@RequiredArgsConstructor
public class ConsoleApplication implements CommandLineRunner {
    private final PlayerService playerService;
    private final ObjectMapper objectMapper;

    @Data
    @Getter
    @Setter
    public static class ConsoleCommand{
        enum CrudType{
            save,
            delete,
            get
        }
        public CrudType crudType = null;
        public Long id = null;
        public String pathToInput = null;
        private String pathToOutput = null;
        public boolean needToHelp = false;
        public boolean needToExit = false;
    }

    public ConsoleCommand parseArgs(String... args){
        ConsoleCommand consoleCommand = new ConsoleCommand();
        if(args.length == 0) {
            consoleCommand.setNeedToHelp(true);
            return consoleCommand;
        }
        for(int i = 0, k = args.length; i < k; i++){
            String e = args[i];
            if(e.equals("exit()") || e.equals("exit") || e.equals("--exit")) {
                consoleCommand.setNeedToExit(true);
                break;
            }else if(e.equals("--crudType") || e.equals("-ct")){
                if(i + 1 >= k){
                    consoleCommand.setNeedToHelp(true);
                    break;
                }else{
                    i++;
                    String crudTypeString = args[i];
                    try {
                        ConsoleCommand.CrudType crudType = ConsoleCommand.CrudType.valueOf(crudTypeString);
                        consoleCommand.setCrudType(crudType);
                    }catch (IllegalArgumentException illegalArgumentException){
                        consoleCommand.setNeedToHelp(true);
                        break;
                    }
                }
            }else if(e.equals("--path-input") || e.equals("-pi")){
                if(i + 1 >= k){
                    consoleCommand.setNeedToHelp(true);
                    break;
                }else {
                    i++;
                    String pathToFile = args[i];
                    if(StringUtils.isBlank(pathToFile)){
                        consoleCommand.setNeedToHelp(true);
                        break;
                    }
                    consoleCommand.setPathToInput(pathToFile);
                }
            } else if(e.equals("--path-output") || e.equals("-po")){
                if(i + 1 >= k){
                    consoleCommand.setNeedToHelp(true);
                    break;
                }else {
                    i++;
                    String pathToFile = args[i];
                    if(StringUtils.isBlank(pathToFile)){
                        consoleCommand.setNeedToHelp(true);
                        break;
                    }
                    consoleCommand.setPathToOutput(pathToFile);
                }
            }else if(e.equals("--id") || e.equals("-i")){
                if(i + 1 >= k){
                    consoleCommand.setNeedToHelp(true);
                    break;
                }else {
                    i++;
                    String id = args[i];
                    try {
                        long l = Long.parseLong(id);
                        consoleCommand.setId(l);
                    }catch (NumberFormatException numberFormatException){
                        consoleCommand.setNeedToHelp(true);
                        break;
                    }
                }
            }
        }
        return consoleCommand;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to console command line!");
        printHelp();

        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("Enter command: ");
            String nextLine = sc.nextLine();
            String[] commands = nextLine.split(" ");
            ConsoleCommand consoleCommand = parseArgs(commands);
            if(consoleCommand.isNeedToExit()){
                break;
            }
            if(consoleCommand.isNeedToHelp()){
                printHelp();
                continue;
            }

            PrintStream outputStream;
            if(consoleCommand.getPathToOutput() == null){
                outputStream = System.out;
            }else{
                File file = new File(consoleCommand.pathToOutput);
                if(!file.exists()){
                    file.createNewFile();
                }
                outputStream = new PrintStream(new FileOutputStream(file));
            }
            if(consoleCommand.getCrudType() != null){
                if(consoleCommand.getCrudType().equals(ConsoleCommand.CrudType.get)){
                    if(consoleCommand.getId() == null){
                        List<PlayerDto> allPlayers = playerService.getAll();
                        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allPlayers);
                        outputStream.println(s);
                    }else{
                        PlayerDto player = playerService.getPlayer(consoleCommand.getId());
                        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(player);
                        outputStream.println(s);
                    }
                } else if (consoleCommand.getCrudType().equals(ConsoleCommand.CrudType.delete)) {
                    if(consoleCommand.getId() == null){
                        List<PlayerDto> allPlayers = playerService.deleteAll();
                        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(allPlayers);
                        outputStream.println(s);
                    }else{
                        PlayerDto player = playerService.delete(consoleCommand.getId());
                        String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(player);
                        outputStream.println(s);
                    }
                }else if(consoleCommand.getCrudType().equals(ConsoleCommand.CrudType.save)){
                    if(StringUtils.isBlank(consoleCommand.getPathToInput())){
                        printHelp();
                        continue;
                    }
                    File f = new File(consoleCommand.getPathToInput());
                    if(!f.exists()){
                        printHelp();
                        continue;
                    }
                    List<PlayerDto> players = null;
                    try {
                        players = Arrays.stream(objectMapper.readValue(f, PlayerDto[].class)).toList();
                    }catch (Exception e){
                        try {
                            PlayerDto playerDto = objectMapper.readValue(f, PlayerDto.class);
                            players = List.of(playerDto);
                        }catch (Exception em){
                            printHelp();
                            continue;
                        }
                    }
                    if(players.isEmpty()){
                        outputStream.println("input file is empty!");
                    }else if(players.size() == 1){
                        PlayerDto res;
                        if(consoleCommand.getId() != null){
                            res = playerService.edit(consoleCommand.getId(), players.get(0));
                        } else{
                            res = playerService.create(players.get(0));
                        }
                        outputStream.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(res));
                    }else{
                        List<PlayerDto> playerDtos = playerService.createAll(players);
                        outputStream.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(playerDtos));
                    }
                }
            }else{
                printHelp();
            }
        }
    }

    public void printHelp(){


    }



}
