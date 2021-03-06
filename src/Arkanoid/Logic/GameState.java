package Arkanoid.Logic;

import Arkanoid.Listener;
import Arkanoid.Logic.models.Ball;
import Arkanoid.Logic.models.Block.*;
import Arkanoid.Logic.models.Board;
import Arkanoid.Logic.models.Prize;
import Arkanoid.Logic.models.Savable;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class GameState implements Savable<GameState> {
    Random random = new Random();
    public static File dataSRC = new File("./db/games/");
    private Player player;
    private Board board;
    private ArrayList<Ball> balls;
    private ArrayList<Block> blocks;
    private ArrayList<Prize> prizes;
    private int playerLives, score;
    public int gameId, addRow = 30000, age;
    private LocalDateTime createdAt;
    private boolean isFinished;
    public String name;

    public GameState() {
        blocks = new ArrayList<>();
        prizes = new ArrayList<>();
        balls = new ArrayList<>();
        board = new Board();
        gameId = Savable.getLastId(dataSRC) + 1;
        createdAt = LocalDateTime.now();
    }

    public void gameOver() {
        isFinished = true;
        pause();
    }

    public void initialSetup() {
        playerLives = 3;
        newBall();
        for (int i = 0; i < 4; i++)
            addBlockRow();
    }

    public Ball newBall() {
        Ball ball = new Ball(board.getX() + board.getLength() / 2, board.getY() - Ball.defaultH);
        addBall(ball);
        return ball;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void start() {
        if (isFinished)
            return;
        ArrayList<Ball> balls1 = new ArrayList<>(balls);
        ArrayList<Prize> prizes1 = new ArrayList<>(prizes);
        ArrayList<Block> blocks1 = new ArrayList<>(blocks);
        for (Ball ball: balls1)
            ball.start();
        for (Prize prize: prizes1)
            prize.fall();
        for (Block block: blocks1)
            block.resume();
        board.resume();
    }

    public void pause() {
        ArrayList<Ball> balls1 = new ArrayList<>(balls);
        ArrayList<Prize> prizes1 = new ArrayList<>(prizes);
        ArrayList<Block> blocks1 = new ArrayList<>(blocks);
        for (Ball ball: balls1)
            ball.pause();
        for (Prize prize: prizes1)
            prize.pause();
        for (Block block: blocks1)
            block.pause();
        board.pause();
    }

    public void addBlockRow() {
        for (Block block: blocks)
            block.shiftDown();
        for (int i = 0; i < 6; i++) {
            Block block;
            int x = 12 + i * 15 + i * Block.defaultWidth;
            int y = Block.YSHIFT, r = random.nextInt(5);
            switch (r) {
                case 0:
                    block = new FlashingBlock(x, y);
                    break;
                case 1:
                    block = new GlassBlock(x, y);
                    break;
                case 2:
                    block = new InvisibleBlock(x, y);
                    break;
                case 3:
                    block = new PrizeBlock(x, y, Prize.PrizeType.RANDOM);
                    break;
                case 4:
                    block = new WoddenBlock(x, y);
                    break;
                default:
                    block = new GlassBlock(x, y);
            }
            addBlock(block);
            block.resume();
        }
    }

    public void addBlock(Block block) {
        block.listener = new Listener() {
            @Override
            public void listen(String s) {
                Scanner scanner = new Scanner(s);
                String res = scanner.next();
                if (Block.Events.valueOf(res) == Block.Events.DELETE)
                    blocks.remove(block);
                else if (Block.Events.valueOf(res) == Block.Events.ADDPRIZE) {
                    String type = scanner.next();
                    int x = scanner.nextInt(), y = scanner.nextInt();
                    Prize prize = new Prize(x, y, Prize.PrizeType.valueOf(type));
                    addPrize(prize);
                    prize.fall();
                }
            }
        };
        blocks.add(block);
    }

    public void addPrize(Prize prize) {
        prize.listener = new Listener() {
            @Override
            public void listen(String s) {
                if (Prize.Events.valueOf(s) == Prize.Events.DELETE)
                    prizes.remove(prize);
            }
        };
        prizes.add(prize);
    }

    public void addBall(Ball ball) {
        ball.listener = new Listener() {
            @Override
            public void listen(String s) {
                if (Ball.Events.valueOf(s) == Ball.Events.DELETE)
                    balls.remove(ball);
            }
        };
        balls.add(ball);
    }

    public void usePrize(Prize.PrizeType prize) {
        System.out.println(prize.toString());
        switch (prize) {
            case EXPANDBOARD:
                board.usePrize(Prize.PrizeType.EXPANDBOARD);
                break;
            case SHRINKBOARD:
                board.usePrize(Prize.PrizeType.SHRINKBOARD);
                break;
            case CONFUSEBOARD:
                board.Confuse();
                break;
            case FASTBALL:
                for(Ball ball: balls)
                    ball.usePrize(Prize.PrizeType.FASTBALL);
                break;
            case SLOWBALL:
                for (Ball ball: balls)
                    ball.usePrize(Prize.PrizeType.SLOWBALL);
                break;
            case MULTIBALL:
                Ball ball1 = newBall();
                Ball ball2 = newBall();
                ball2.setxSpeed(-ball2.getxSpeed());
                start();
                break;
            case FIREBALL:
                ArrayList<Ball> balls1 = new ArrayList<>(balls);
                for (Ball ball: balls1)
                    ball.usePrize(prize);
                break;
            case RANDOM:
                int len = Prize.PrizeType.values().length;
                len -= 1;
                Random random = new Random();
                usePrize(Prize.PrizeType.values()[random.nextInt(len)]);
                break;
        }
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Board getBoard() {
        return board;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public ArrayList<Prize> getPrizes() {
        return prizes;
    }

    public void setPlayerLives(int playerLives) {
        this.playerLives = playerLives;
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int m) {
        score += m;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public boolean isFirstSave() {
        if ((new File(dataSRC.getPath() + "/" + gameId + "/state")).exists())
            return false;
        return true;
    }

    public String getCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdAt.format(formatter);
    }

    @Override
    public String serialize() {
        String res = gameId + "\n" +
                createdAt + "\n" +
                playerLives + "\n" +
                score + "\n" +
                player.id + "\n" +
                isFinished + "\n" +
                addRow + "\n" +
                name + "\n" +
                age + "\n";
        return res;
    }

    @Override
    public void deserialize(Scanner serialized) {
        gameId = serialized.nextInt();
        createdAt = LocalDateTime.parse(serialized.next());
        playerLives = serialized.nextInt();
        score = serialized.nextInt();
        player = new Player("");
        player.id = serialized.nextInt();
        isFinished = serialized.nextBoolean();
        addRow = serialized.nextInt();
        name = serialized.next();
        age = serialized.nextInt();
    }

    @Override
    public void extraLoad(File path) {
        player.load(new File(Player.dataSRC + "/" + player.id));

        board.load(new File(path.getPath() + "/board"));
        int cnt = 0;

        if ((new File(path.getPath() + "/balls/0")).exists()) {
            cnt = (Objects.requireNonNull(new File(path.getPath() + "/balls/").list())).length;
            balls.clear();
            for (int i = 0; i < cnt; i++) {
                Ball ball = new Ball(0, 0);
                ball.load(new File(path.getPath() + "/balls/" + i));
                addBall(ball);
            }
        }

        if ((new File(path.getPath() + "/blocks/0")).exists()) {
            cnt = (Objects.requireNonNull(new File(path.getPath() + "/blocks/").list())).length;
            blocks.clear();
            for (int i = 0; i < cnt; i++) {
                Block block = Block.loadBlock(new File(path.getPath() + "/blocks/" + i));
                assert block != null;
                addBlock(block);
            }
        }

        if ((new File(path.getPath() + "/prizes/0")).exists()) {
            cnt = (Objects.requireNonNull(new File(path.getPath() + "/prizes/").list())).length;
            prizes.clear();
            for (int i = 0; i < cnt; i++) {
                Prize prize = new Prize(0, 0, Prize.PrizeType.RANDOM);
                prize.load(new File(path.getPath() + "/prizes/" + i));
                addPrize(prize);
            }
        }
    }

    @Override
    public void extraSave(File path) {
        board.save(new File(path.getPath() + "/board"));
        deleteDir(new File(path.getPath() + "/balls"));
        deleteDir(new File(path.getPath() + "/blocks"));
        deleteDir(new File(path.getPath() + "/prizes"));
        for (int i = 0; i < balls.size(); i++)
            balls.get(i).save(new File(path.getPath() + "/balls/" + i));
        for (int i = 0; i < blocks.size(); i++)
            blocks.get(i).save(new File(path.getPath() + "/blocks/" + i));
        for (int i = 0; i < prizes.size(); i++)
            prizes.get(i).save(new File(path.getPath() + "/prizes/" + i));
    }

    static void deleteDir(File path) {
        if (!path.exists())
            return;
        for(String p: Objects.requireNonNull(path.list())){
            File currentFile = new File(path.getPath(),p);
            currentFile.delete();
        }
    }
}
