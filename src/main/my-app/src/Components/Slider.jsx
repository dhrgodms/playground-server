import {
    Button,
    Drawer, DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay, Flex, Heading, IconButton, Input,
    useDisclosure
} from "@chakra-ui/react";
import React from "react";
import {useNavigate} from "react-router-dom";
import {HamburgerIcon} from "@chakra-ui/icons";
export const Slider = () => {
    const navigate = useNavigate();
    const { isOpen, onOpen, onClose } = useDisclosure();
    const btnRef = React.useRef();

    return (
        <><Flex m={'5'} width={"full"} justify={"space-between"}>
            <Flex gap={'4'}>
            <IconButton ref={btnRef} icon={<HamburgerIcon/>} colorScheme="green" onClick={onOpen}  aria-label={'hambuger'}/>
            <Button
                ref={btnRef}
                colorScheme="pink"
                onClick={() => navigate('/login')}
            >
                LOG IN
            </Button>
            </Flex>
            <Button
                ref={btnRef}
                colorScheme="yellow"
                onClick={() => navigate('/upload')}
            >
                UPLOAD
            </Button>
        </Flex>
            <Drawer
                isOpen={isOpen}
                placement="left"
                onClose={onClose}
                finalFocusRef={btnRef}
            >
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader ><Heading size={"lg"} mb={3}>안녕! 🤙</Heading>
                        <Heading size={"lg"} mb={3}>여긴 🆗 Haeeun.zip</Heading></DrawerHeader>


                    <DrawerBody>
                        <Flex direction={'column'} gap={'1.5em'}>
                            <Input placeholder="Type here..." />
                            <Button
                                ref={btnRef}
                                colorScheme="teal"
                                variant={'ghost'}
                                onClick={() => navigate('/')}
                            >
                                메인으로
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="red"
                                variant={'ghost'}
                                onClick={() => navigate('/best')}
                            >
                                인기글
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="teal"
                                variant={'ghost'}
                                onClick={() => navigate('/recent')}
                            >
                                최신글
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="green"
                                variant={'ghost'}
                                onClick={() => navigate('/writes')}
                            >
                                생각글
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="green"
                                variant={'ghost'}
                                onClick={() => navigate('/toons')}
                            >
                                일상만화
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="green"
                                variant={'ghost'}
                                onClick={() => navigate('/playlists')}
                            >
                                내가 듣는 플레이리스트(Playlist)
                            </Button>
                            <Button
                                ref={btnRef}
                                colorScheme="pink"
                                variant={'ghost'}
                                onClick={() => navigate('/guestbook')}
                            >
                                어서오세요 방명록
                            </Button>
                        </Flex>
                    </DrawerBody>

                    <DrawerFooter>
                        <Button variant="outline" mr={3} onClick={onClose}>
                            Cancel
                        </Button>
                        <Button colorScheme="blue">Save</Button>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    );
};